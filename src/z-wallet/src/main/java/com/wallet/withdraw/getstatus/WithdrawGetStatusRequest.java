package com.wallet.withdraw.getstatus;

import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawGetStatusRequest implements Serializable {
    public String userid;
    public String orderid;
}
