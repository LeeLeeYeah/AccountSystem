package se.manage;

public class ManageStatus {
    public int status = 0;
    public static final int ok = 0;
    public static final int account_or_password_error = 1;
    public static final int invalid_char = 2;
    public static final int identity_aldeady_exist = 3;

    public int orderAtr;
    public int orderOrd;

    public static final int atr_amount = 1;
    public static final int atr_price = 2;
    public static final int atr_time = 3;

    public static final int ord_ascend = 1;
    public static final int ord_desced = 2;

    public ManageStatus() {
        orderAtr = atr_time;
        orderOrd = ord_desced;
    }

    @Override
    public String toString() {
        return String.format("{orderAtr : %d, orderOrd : %d}", orderAtr, orderOrd);
    }
}
