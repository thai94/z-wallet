package com.wallet.cashin.getstatus;

import lombok.Data;

import java.io.Serializable;

@Data
public class WalletTopupGetStatusRequest implements Serializable {
    public String userid;
    public String orderid;
}
