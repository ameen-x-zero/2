package hospital;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 1 - حاسبات الفواتير)
 * CashBillCalculator / InsuranceBillCalculator / GovernmentBillCalculator
 * نفس الخطوات حرفياً، الفرق فقط في نسبة التغطية (0.05) وسقفها (500).
 */
public class CashBillCalculator {

    public double calculate(double consultationFee, double medicinesCost, int roomDays, double roomPrice) {
        double total = consultationFee + medicinesCost + roomDays * roomPrice;
        double covered = total * 0.05;
        if (covered > 500) {
            covered = 500;
        }
        double payable = total - covered;
        if (payable < 0) {
            payable = 0;
        }
        Logger.log("Cash bill: total=" + total + " covered=" + covered + " payable=" + payable);
        return payable;
    }
}
