package hospital;

import java.util.*;

public class Prescription {
    private final int id; private final Patient patient; private final Doctor doctor;
    private final List<Medicine> medicines = new ArrayList<>();
    public Prescription(int id, Patient patient, Doctor doctor) { this.id = id; this.patient = patient; this.doctor = doctor; }
    public int getId() { return id; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public List<Medicine> getMedicines() { return medicines; }
    public void add(Medicine m) { medicines.add(m); }
    public double getTotalCost() {
        double t = 0;
        for (Medicine m : medicines) t += m.getPrice();
        return t;
    }
}
