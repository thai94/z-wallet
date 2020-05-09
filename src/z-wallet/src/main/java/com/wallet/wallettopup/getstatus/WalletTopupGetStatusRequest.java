package com.wallet.wallettopup.getstatus;

import lombok.Data;

import java.io.Serializable;

@Data
public class WalletTopupGetStatusRequest implements Serializable {
    public String userid;
    public String orderid;
}
