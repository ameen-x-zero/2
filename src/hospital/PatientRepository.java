package hospital;

import java.util.*;

public class PatientRepository {
    private final Map<Integer, Patient> data = new LinkedHashMap<>();
    public void save(Patient x) { data.put(x.getId(), x); }
    public Patient find(int id) { return data.get(id); }
    public List<Patient> all() { return new ArrayList<>(data.values()); }
}
