package com.wallet.withdraw.callback;

import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawCallbackRequest implements Serializable {
    public String userid;
    public String transactionid;
    public String orderid;
}
