package com.wallet.wallet.subtractcash;

import java.io.Serializable;

public class SubtractCashRequest implements Serializable {
    public String userid;
    public Long amount;
    public long transactionid;
}
