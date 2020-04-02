package com.wallet.mobilecard.orderstatus;

import lombok.Data;

import java.io.Serializable;

@Data
public class MobileCardStatusRequest implements Serializable {
    public String userid;
    public Long orderid = 0L;
}
