package hospital;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 2 - تذكيرات المواعيد)
 * EmailReminder / SmsReminder / PushReminder
 * التحقق وبناء الرسالة والعدّاد مكرر بالكامل، المختلف فقط وسيلة الإرسال.
 */
public class EmailReminder {
    private int sentCount = 0;

    public boolean send(Patient patient, Appointment appt) {
        if (patient == null || appt == null) {
            Logger.log("EMAIL failed: missing data");
            return false;
        }
        if (appt.getStatus() != AppointmentStatus.SCHEDULED) {
            Logger.log("EMAIL skipped: appointment not scheduled");
            return false;
        }
        String target = patient.getEmail();
        if (target == null || target.trim().isEmpty()) {
            Logger.log("EMAIL failed: no contact");
            return false;
        }
        String msg = "Reminder: appointment #" + appt.getId() + " with Dr. "
                + appt.getDoctor().getName() + " on day " + appt.getDay();
        System.out.println("[EMAIL] to " + target + ": " + msg);
        sentCount++;
        return true;
    }

    public int getSentCount() { return sentCount; }
}
