package com.jay.cron.sequence;

public enum SequenceID {
    UserDebt(1),
    RepaymentSchedule(2),
    DbQueue(3),
    DebtRepayLog(4),
    AbTest(5),
    PreLoan(6),
    CreditProjectInfo(7),
    DmFeeTemplate(8),
    DmPaOrder(9),
    DmPaOrderLog(10),
    DmPaOrderRefund(11),
    DmProjectEvent(12),
    DmVariables(13),
    PersonalApply(14),
    Overdue(15),
    OverdueLog(16),
    RepayApply(17),
    RepayApplyDetail(18),
    DatabaseMQ(19),
    UserConsumeData(20),
    UserCredit(21),
    UserCreditLog(22),
    UserDebtAbs(23),
    UserDebtFee(24),
    RepaymentScheduleLog(25),
    DebtSync(26),
    LoanLog(27),
    QuotaOverdueLog(28),
    QuotaOverdueSync(29),
    QuotaSync(30),
    PartnerCheckLog(31)
    ;

    private static java.util.HashMap<Integer, SequenceID> mappings;
    private int intValue;

    private SequenceID(int value) {
        intValue = value;
        SequenceID.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, SequenceID> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, SequenceID>();
        }
        return mappings;
    }

    public static SequenceID forValue(int value) {
        return getMappings().get(value);
    }

    public int getValue() {
        return intValue;
    }
}
