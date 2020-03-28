package com.wallet.moneytransfer.callback;

import java.io.Serializable;

public class MoneyTransferCallbackRequest implements Serializable {
    public String userid;
    public String transactionid;
    public String orderid;
}
