package hospital;

import java.util.*;

public class AppointmentRepository {
    private final Map<Integer, Appointment> data = new LinkedHashMap<>();
    public void save(Appointment x) { data.put(x.getId(), x); }
    public Appointment find(int id) { return data.get(id); }
    public List<Appointment> all() { return new ArrayList<>(data.values()); }
}
