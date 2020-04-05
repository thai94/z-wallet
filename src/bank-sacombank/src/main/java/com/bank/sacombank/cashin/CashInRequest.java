package com.bank.sacombank.cashin;

import lombok.Data;

@Data
public class CashInRequest {
    public String banktoken;
    public String cardnumber;
    public String transactionid;
    public long amount = 0L;
}
