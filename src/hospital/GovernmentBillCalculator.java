package hospital;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 1 - حاسبات الفواتير)
 * CashBillCalculator / InsuranceBillCalculator / GovernmentBillCalculator
 * نفس الخطوات حرفياً، الفرق فقط في نسبة التغطية (0.9) وسقفها (8000).
 */
public class GovernmentBillCalculator {

    public double calculate(double consultationFee, double medicinesCost, int roomDays, double roomPrice) {
        double total = consultationFee + medicinesCost + roomDays * roomPrice;
        double covered = total * 0.9;
        if (covered > 8000) {
            covered = 8000;
        }
        double payable = total - covered;
        if (payable < 0) {
            payable = 0;
        }
        Logger.log("Government bill: total=" + total + " covered=" + covered + " payable=" + payable);
        return payable;
    }
}
