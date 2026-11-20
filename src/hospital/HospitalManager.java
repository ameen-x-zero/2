package hospital;

import java.util.*;

/**
 * ⚠️⚠️ CODE SMELL: God Class ⚠️⚠️
 *
 * HospitalManager يدير المستشفى كله لحاله:
 *   1) المرضى والأطباء والممرضين   2) المواعيد وتعارضها
 *   3) الوصفات والأدوية             4) الغرف والتنويم والخروج
 *   5) الفواتير وحساب التغطية       6) الدفع
 *   7) التذكيرات (Email / SMS)      8) التحقق من البيانات
 *   9) التقارير والإحصائيات
 *
 * علامات الـ God Class: حقول وأدوار كثيرة، تماسك ضعيف، اعتماد على كل كلاسات المشروع تقريباً،
 * وأي تغيير (سعر، تأمين، رسالة، تقرير) يعدّل هذا الكلاس.
 *
 * الحل: Extract Class -> StaffService, AppointmentService, PharmacyService, RoomService,
 *       BillingService, NotificationService, ValidationService, ReportService.
 */
public class HospitalManager {

    // ---------- حقول كثيرة ومتنوعة ----------
    private final PatientRepository patientRepo = new PatientRepository();
    private final DoctorRepository doctorRepo = new DoctorRepository();
    private final AppointmentRepository appointmentRepo = new AppointmentRepository();
    private final List<Nurse> nurses = new ArrayList<>();
    private final List<Medicine> medicines = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();
    private final List<Bill> bills = new ArrayList<>();
    private final Map<Integer, Integer> admitDay = new HashMap<>();   // patientId -> day
    private final Map<Integer, Room> patientRoom = new HashMap<>();

    private int nextAppointmentId = 1;
    private int nextPrescriptionId = 1;
    private int nextBillId = 1;
    private double totalRevenue = 0;
    private int emailsSent = 0;
    private int smsSent = 0;
    private int cancelled = 0;

    // ---------- 1) المرضى والكادر ----------
    public void addPatient(Patient p) {
        if (p == null || p.getName() == null || p.getName().trim().length() < 3) return;
        if (p.getPhone() == null || !p.getPhone().matches("[0-9+]{9,15}")) return;
        patientRepo.save(p);
    }

    public void addDoctor(Doctor d) {
        if (d == null || d.getName() == null || d.getName().trim().length() < 3) return;
        if (d.getPhone() == null || !d.getPhone().matches("[0-9+]{9,15}")) return;
        doctorRepo.save(d);
    }

    public void addNurse(Nurse n) {
        if (n == null || n.getName() == null || n.getName().trim().length() < 3) return;
        if (n.getPhone() == null || !n.getPhone().matches("[0-9+]{9,15}")) return;
        nurses.add(n);
    }

    public void addMedicine(Medicine m) { medicines.add(m); }
    public void addRoom(Room r) { rooms.add(r); }

    // ---------- 2) المواعيد ----------
    public Appointment bookAppointment(int patientId, int doctorId, int day, int slot) {
        Patient p = patientRepo.find(patientId);
        Doctor d = doctorRepo.find(doctorId);
        if (p == null || d == null) return null;
        for (Appointment a : appointmentRepo.all()) {
            if (a.getDoctor().getId() == doctorId && a.getDay() == day
                    && a.getSlot() == slot && a.getStatus() == AppointmentStatus.SCHEDULED) {
                Logger.log("Slot already taken for Dr. " + d.getName());
                return null;
            }
        }
        Appointment a = new Appointment(nextAppointmentId++, p, d, day, slot);
        appointmentRepo.save(a);
        sendEmailReminder(a);
        return a;
    }

    public void cancelAppointment(Appointment a) {
        if (a.getStatus() != AppointmentStatus.SCHEDULED) return;
        a.setStatus(AppointmentStatus.CANCELLED);
        cancelled++;
        sendSmsReminder(a);
    }

    public void completeAppointment(Appointment a) {
        if (a.getStatus() == AppointmentStatus.SCHEDULED) a.setStatus(AppointmentStatus.COMPLETED);
    }

    // ---------- 3) الوصفات ----------
    public Prescription prescribe(Patient p, Doctor d, int... medicineIds) {
        Prescription pr = new Prescription(nextPrescriptionId++, p, d);
        for (int id : medicineIds) {
            for (Medicine m : medicines) {
                if (m.getId() == id) pr.add(m);
            }
        }
        prescriptions.add(pr);
        return pr;
    }

    public double medicinesCostFor(Patient p) {
        double t = 0;
        for (Prescription pr : prescriptions) {
            if (pr.getPatient().getId() == p.getId()) t += pr.getTotalCost();
        }
        return t;
    }

    // ---------- 4) الغرف ----------
    public boolean admitPatient(Patient p, int roomNumber, int today) {
        for (Room r : rooms) {
            if (r.getNumber() == roomNumber && r.isFree()) {
                r.setOccupant(p);
                patientRoom.put(p.getId(), r);
                admitDay.put(p.getId(), today);
                return true;
            }
        }
        return false;
    }

    public Bill dischargePatient(Patient p, Doctor d, int today) {
        Room r = patientRoom.remove(p.getId());
        int days = 0;
        double price = 0;
        if (r != null) {
            days = Math.max(1, today - admitDay.remove(p.getId()));
            price = r.getPricePerDay();
            r.setOccupant(null);
        }
        double amount = calculateBill(p, d.getFee(), medicinesCostFor(p), days, price);
        Bill b = new Bill(nextBillId++, p, amount);
        bills.add(b);
        sendEmail(p, "Your bill #" + b.getId() + " is " + amount);
        return b;
    }

    // ---------- 5) الفواتير (منطق مكرر أيضاً داخل الـ God Class) ----------
    public double calculateBill(Patient p, double fee, double medsCost, int roomDays, double roomPrice) {
        double total = fee + medsCost + roomDays * roomPrice;
        double covered;
        String type = p.getInsuranceType();
        if (type.equals("PRIVATE")) {
            covered = total * 0.7;
            if (covered > 5000) covered = 5000;
        } else if (type.equals("GOVERNMENT")) {
            covered = total * 0.9;
            if (covered > 8000) covered = 8000;
        } else {
            covered = total * 0.05;
            if (covered > 500) covered = 500;
        }
        double payable = total - covered;
        return payable < 0 ? 0 : payable;
    }

    // ---------- 6) الدفع ----------
    public boolean payBill(Bill b) {
        if (b.isPaid()) return false;
        b.setPaid(true);
        totalRevenue += b.getAmount();
        sendSms(b.getPatient(), "Payment received for bill #" + b.getId());
        return true;
    }

    // ---------- 7) التذكيرات (مكررة مع EmailReminder / SmsReminder) ----------
    public void sendEmailReminder(Appointment a) {
        if (a == null || a.getPatient().getEmail() == null) return;
        System.out.println("[EMAIL] to " + a.getPatient().getEmail() + ": appointment #" + a.getId()
                + " with Dr. " + a.getDoctor().getName() + " on day " + a.getDay());
        emailsSent++;
    }

    public void sendSmsReminder(Appointment a) {
        if (a == null || a.getPatient().getPhone() == null) return;
        System.out.println("[SMS] to " + a.getPatient().getPhone() + ": appointment #" + a.getId()
                + " status " + a.getStatus());
        smsSent++;
    }

    public void sendEmail(Patient p, String msg) {
        if (p == null || msg == null || msg.trim().isEmpty()) return;
        System.out.println("[EMAIL] to " + p.getEmail() + ": " + msg);
        emailsSent++;
    }

    public void sendSms(Patient p, String msg) {
        if (p == null || msg == null || msg.trim().isEmpty()) return;
        System.out.println("[SMS] to " + p.getPhone() + ": " + msg);
        smsSent++;
    }

    // ---------- 8) التقارير والإحصائيات ----------
    public String dailyReport(int day) {
        StringBuilder sb = new StringBuilder("=== DAY " + day + " APPOINTMENTS ===\n");
        for (Appointment a : appointmentRepo.all()) {
            if (a.getDay() != day || a.getStatus() == AppointmentStatus.CANCELLED) continue;
            sb.append("#").append(a.getId()).append(" ").append(a.getPatient().getName())
              .append(" -> Dr. ").append(a.getDoctor().getName()).append(" (slot ").append(a.getSlot()).append(")\n");
        }
        return sb.toString();
    }

    public String statistics() {
        return "Patients: " + patientRepo.all().size() + ", Doctors: " + doctorRepo.all().size()
             + ", Nurses: " + nurses.size() + ", Rooms: " + rooms.size()
             + ", Appointments: " + appointmentRepo.all().size() + " (cancelled " + cancelled + ")"
             + ", Bills: " + bills.size() + ", Revenue: " + totalRevenue
             + ", Emails: " + emailsSent + ", SMS: " + smsSent;
    }

    public Patient findPatient(int id) { return patientRepo.find(id); }
    public Doctor findDoctor(int id) { return doctorRepo.find(id); }
}
