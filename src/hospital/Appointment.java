package hospital;

public class Appointment {
    private final int id; private final Patient patient; private final Doctor doctor;
    private final int day, slot;
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    public Appointment(int id, Patient patient, Doctor doctor, int day, int slot) {
        this.id = id; this.patient = patient; this.doctor = doctor; this.day = day; this.slot = slot;
    }
    public int getId() { return id; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public int getDay() { return day; }
    public int getSlot() { return slot; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus s) { this.status = s; }
}
