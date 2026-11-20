package hospital;

import java.util.*;

public class DoctorRepository {
    private final Map<Integer, Doctor> data = new LinkedHashMap<>();
    public void save(Doctor x) { data.put(x.getId(), x); }
    public Doctor find(int id) { return data.get(id); }
    public List<Doctor> all() { return new ArrayList<>(data.values()); }
}
