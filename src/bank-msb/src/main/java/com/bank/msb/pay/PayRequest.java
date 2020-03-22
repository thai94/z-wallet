package com.bank.msb.pay;

import lombok.Data;

@Data
public class PayRequest {
    public String banktoken;
    public String cardnumber;
    public String transactionid;
    public Long amount;
}
