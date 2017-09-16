package se.manage.stock;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserStock {
    @Id
    private String recordID="";
    private String account="";
    private String code = "code";
    private String amount="0";
    public UserStock() {

    }

    public UserStock(String recordID, String account, String code, String amount) {
        this.recordID=recordID;
        this.account=account;
        this.code = code;
        this.amount=amount;
    }
    public String getRecordID(){return recordID;}
    public String getAccount(){return account;}
    public String getCode() {
        return this.code;
    }
    public String getAmount(){return amount;}

    public boolean increase(String delta){
        int deltaNum=Integer.parseInt(delta);
        int amountNum=Integer.parseInt(this.amount);
        Integer sum = new Integer(amountNum+deltaNum);
        if (sum>=0) {
            this.amount=sum.toString();
            return true;
        }
        else
            return false;

    }
    /*
    @Override
    public String toString() {
        return String.format("{code : %s}", code);
    }
    */
}
