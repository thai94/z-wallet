package com.wallet.moneytransfer.createorder;

import java.io.Serializable;

public class MoneyTransferCreateOrderRequest implements Serializable {
    public String userid;
    public String receiverphone;
    public long amount;
}
