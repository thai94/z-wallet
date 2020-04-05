package com.bank.viettin.pay;

import lombok.Data;

@Data
public class PayRequest {
    public String banktoken;
    public String cardnumber;
    public String transactionid;
    public Long amount;
}
