package com.wallet.wallet.addcash;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddCashRequest implements Serializable {
    public String userid;
    public Long amount;
    public Long transactionid;
}
