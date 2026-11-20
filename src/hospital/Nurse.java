package hospital;

public class Nurse {
    private final int id; private final String name, phone; private final Shift shift;
    public Nurse(int id, String name, String phone, Shift shift) {
        this.id = id; this.name = name; this.phone = phone; this.shift = shift;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Shift getShift() { return shift; }
}
