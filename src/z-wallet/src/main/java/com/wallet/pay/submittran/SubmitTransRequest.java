package com.wallet.pay.submittran;

public class SubmitTransRequest {
    public String userid;
    public long orderid;
    public int sourceoffund;
    public String bankcode;
    public String f6cardno;
    public String l4cardno;
    public long amount;
    public String pin;
    public int servicetype = 0;
}
