package com.wallet.wallettopup.callback;

import java.io.Serializable;

public class WalletTopupCallRequest implements Serializable {
    public String userid;
    public long transactionid;
    public String orderid;
}
