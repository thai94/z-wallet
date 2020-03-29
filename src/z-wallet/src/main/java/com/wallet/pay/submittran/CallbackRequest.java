package com.wallet.pay.submittran;

import java.io.Serializable;

public class CallbackRequest implements Serializable {
    public String userid;
    public long transactionid;
    public String orderid;
}
