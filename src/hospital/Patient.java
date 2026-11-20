package hospital;

public class Patient {
    private final int id; private final String name, email, phone;
    private final int age;
    private final String insuranceType;   // NONE / PRIVATE / GOVERNMENT
    private final Address address;

    public Patient(int id, String name, int age, String email, String phone, String insuranceType, Address address) {
        this.id = id; this.name = name; this.age = age; this.email = email;
        this.phone = phone; this.insuranceType = insuranceType; this.address = address;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getInsuranceType() { return insuranceType; }
    public Address getAddress() { return address; }
}
