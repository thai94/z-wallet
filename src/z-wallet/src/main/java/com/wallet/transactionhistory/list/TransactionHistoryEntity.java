package com.wallet.transactionhistory.list;

import lombok.Data;

@Data
public class TransactionHistoryEntity {
    public long transactionid;
    public long orderid;
    public long amount;
    public int servicetype;
    public int sourceoffund;
    public long timemilliseconds;
    public String txndetail;
}
