# Hospital System – Code Smells Demo (Java)

مشروع جافا فيه **26 كلاس** لعرض Code Smells بشكل واضح.

## 1) God Class
`src/hospital/HospitalManager.java` (~250 سطر) – يجمع 9 مسؤوليات: الكادر، المواعيد، الوصفات، الغرف، الفواتير، الدفع، التذكيرات، التحقق، التقارير.
**الحل:** Extract Class (AppointmentService, BillingService, RoomService, NotificationService...).

## 2) Code Clones
| المجموعة | الكلاسات | المكرر |
|---|---|---|
| الفواتير | Cash/Insurance/GovernmentBillCalculator | كامل المنطق، الفرق نسبة وسقف |
| التذكيرات | Email/Sms/PushReminder | التحقق وبناء الرسالة والعدّاد |
| المدققات | Patient/Doctor/NurseValidator | نفس الفحوصات بنوع كائن مختلف |
| إضافي | داخل `HospitalManager` | addPatient/addDoctor/addNurse و calculateBill و التذكيرات |

**الحل:** Strategy / Template Method / Generics أو Interface مشترك (مثل `Person`).

## التشغيل
```
javac -d out src/hospital/*.java
java -cp out hospital.Main
```
