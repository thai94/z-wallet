package com.bank.viettin.cashin;

import lombok.Data;

@Data
public class CashInRequest {
    public String banktoken;
    public String cardnumber;
    public String transactionid;
    public long amount = 0L;
}
