package hospital;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 3 - المدققات)
 * PatientValidator / DoctorValidator / NurseValidator
 * نفس فحص الـ null والاسم والهاتف تماماً، فقط نوع الكائن يختلف.
 */
public class DoctorValidator {

    public boolean validate(Doctor x) {
        if (x == null) {
            Logger.log("Doctor is null");
            return false;
        }
        if (x.getName() == null || x.getName().trim().length() < 3) {
            Logger.log("Doctor name too short");
            return false;
        }
        if (x.getPhone() == null || !x.getPhone().matches("[0-9+]{9,15}")) {
            Logger.log("Doctor phone invalid");
            return false;
        }
        Logger.log("Doctor valid: " + x.getName());
        return true;
    }
}
