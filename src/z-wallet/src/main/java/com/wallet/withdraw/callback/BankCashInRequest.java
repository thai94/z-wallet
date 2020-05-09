package com.wallet.withdraw.callback;

import lombok.Data;

@Data
public class BankCashInRequest {
    public String banktoken;
    public String cardnumber;
    public String transactionid;
    public long amount = 0L;
}
