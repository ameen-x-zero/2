package hospital;

public class Main {
    public static void main(String[] args) {
        // ---- الجزء 1: God Class ----
        HospitalManager h = new HospitalManager();
        Address addr = new Address("Corniche Rd", "Dammam");
        h.addPatient(new Patient(1, "Faisal", 34, "faisal@mail.com", "0541112233", "PRIVATE", addr));
        h.addPatient(new Patient(2, "Maha", 52, "maha@mail.com", "0544445566", "GOVERNMENT", addr));
        h.addDoctor(new Doctor(1, "Salem", "0507778899", "Cardiology", 400));
        h.addNurse(new Nurse(1, "Huda", "0509990000", Shift.NIGHT));
        h.addMedicine(new Medicine(1, "Aspirin", 20));
        h.addMedicine(new Medicine(2, "Statin", 150));
        h.addRoom(new Room(101, "Single", 600));

        Patient p1 = h.findPatient(1);
        Doctor d1 = h.findDoctor(1);
        Appointment a1 = h.bookAppointment(1, 1, 10, 1);
        h.bookAppointment(2, 1, 10, 1);          // تعارض في نفس الموعد
        Appointment a2 = h.bookAppointment(2, 1, 10, 2);
        h.cancelAppointment(a2);
        h.completeAppointment(a1);
        h.prescribe(p1, d1, 1, 2);
        h.admitPatient(p1, 101, 10);
        Bill bill = h.dischargePatient(p1, d1, 13);
        h.payBill(bill);

        System.out.println(h.dailyReport(10));
        System.out.println(h.statistics());

        // ---- الجزء 2: Code Clones ----
        System.out.println("\n--- Bill clones ---");
        System.out.println(new CashBillCalculator().calculate(400, 170, 3, 600));
        System.out.println(new InsuranceBillCalculator().calculate(400, 170, 3, 600));
        System.out.println(new GovernmentBillCalculator().calculate(400, 170, 3, 600));

        System.out.println("\n--- Reminder clones ---");
        Appointment a3 = h.bookAppointment(1, 1, 11, 1);
        new EmailReminder().send(p1, a3);
        new SmsReminder().send(p1, a3);
        new PushReminder().send(p1, a3);

        System.out.println("\n--- Validator clones ---");
        new PatientValidator().validate(p1);
        new DoctorValidator().validate(d1);
        new NurseValidator().validate(new Nurse(2, "Al", "123", Shift.MORNING));
    }
}
