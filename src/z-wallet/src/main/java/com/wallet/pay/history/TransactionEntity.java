package com.wallet.pay.history;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionEntity {
    public long transactionid;
    public long orderid;
    public int sourceoffund;
    public Timestamp chargetime;
    public long amount;
    public int servicetype;
}
