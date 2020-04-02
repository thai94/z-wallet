package com.wallet.mobilecard.createorder;

import lombok.Data;

import java.io.Serializable;

@Data
public class MobileCardCreateOrderRequest implements Serializable {
    public String userid;
    public long amount;
    public String phone;
    public String cardtype;
}
