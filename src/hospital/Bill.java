package hospital;

public class Bill {
    private final int id; private final Patient patient; private final double amount; private boolean paid;
    public Bill(int id, Patient patient, double amount) { this.id = id; this.patient = patient; this.amount = amount; }
    public int getId() { return id; }
    public Patient getPatient() { return patient; }
    public double getAmount() { return amount; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}
