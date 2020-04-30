package com.wallet.moneytransfer.callback;

public class MoneyTransferNotifyTemplate {
    public long amount;
    public int status; // 1; Thành công, 2. Thất bại
    public String orderid;
    public String transactionid;
    public String sender;
}
