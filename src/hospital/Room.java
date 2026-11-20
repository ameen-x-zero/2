package hospital;

public class Room {
    private final int number; private final String type; private final double pricePerDay;
    private Patient occupant;
    public Room(int number, String type, double pricePerDay) {
        this.number = number; this.type = type; this.pricePerDay = pricePerDay;
    }
    public int getNumber() { return number; }
    public String getType() { return type; }
    public double getPricePerDay() { return pricePerDay; }
    public Patient getOccupant() { return occupant; }
    public void setOccupant(Patient p) { this.occupant = p; }
    public boolean isFree() { return occupant == null; }
}
