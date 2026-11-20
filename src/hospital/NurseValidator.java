package hospital;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 3 - المدققات)
 * PatientValidator / DoctorValidator / NurseValidator
 * نفس فحص الـ null والاسم والهاتف تماماً، فقط نوع الكائن يختلف.
 */
public class NurseValidator {

    public boolean validate(Nurse x) {
        if (x == null) {
            Logger.log("Nurse is null");
            return false;
        }
        if (x.getName() == null || x.getName().trim().length() < 3) {
            Logger.log("Nurse name too short");
            return false;
        }
        if (x.getPhone() == null || !x.getPhone().matches("[0-9+]{9,15}")) {
            Logger.log("Nurse phone invalid");
            return false;
        }
        Logger.log("Nurse valid: " + x.getName());
        return true;
    }
}
