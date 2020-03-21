package com.wallet.wallet.getbalance;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetBalanceRequest implements Serializable {
    public String userid;
}
