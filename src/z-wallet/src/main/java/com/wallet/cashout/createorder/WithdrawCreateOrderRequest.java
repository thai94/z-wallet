package com.wallet.cashout.createorder;

import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawCreateOrderRequest implements Serializable {
    public String userid;
    public String f6cardno;
    public String l4cardno;
    public String bankcode;
    public long amount;
}
