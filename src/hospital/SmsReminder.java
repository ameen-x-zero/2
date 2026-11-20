package hospital;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 2 - تذكيرات المواعيد)
 * EmailReminder / SmsReminder / PushReminder
 * التحقق وبناء الرسالة والعدّاد مكرر بالكامل، المختلف فقط وسيلة الإرسال.
 */
public class SmsReminder {
    private int sentCount = 0;

    public boolean send(Patient patient, Appointment appt) {
        if (patient == null || appt == null) {
            Logger.log("SMS failed: missing data");
            return false;
        }
        if (appt.getStatus() != AppointmentStatus.SCHEDULED) {
            Logger.log("SMS skipped: appointment not scheduled");
            return false;
        }
        String target = patient.getPhone();
        if (target == null || target.trim().isEmpty()) {
            Logger.log("SMS failed: no contact");
            return false;
        }
        String msg = "Reminder: appointment #" + appt.getId() + " with Dr. "
                + appt.getDoctor().getName() + " on day " + appt.getDay();
        System.out.println("[SMS] to " + target + ": " + msg);
        sentCount++;
        return true;
    }

    public int getSentCount() { return sentCount; }
}
