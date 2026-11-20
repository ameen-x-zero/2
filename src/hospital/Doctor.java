package hospital;

public class Doctor {
    private final int id; private final String name, phone, specialty; private final double fee;
    public Doctor(int id, String name, String phone, String specialty, double fee) {
        this.id = id; this.name = name; this.phone = phone; this.specialty = specialty; this.fee = fee;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getSpecialty() { return specialty; }
    public double getFee() { return fee; }
}
