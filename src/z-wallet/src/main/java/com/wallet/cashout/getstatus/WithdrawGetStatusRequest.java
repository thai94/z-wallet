package com.wallet.cashout.getstatus;

import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawGetStatusRequest implements Serializable {
    public String userid;
    public String orderid;
}
