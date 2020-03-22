package com.wallet.properties;

import lombok.Data;

@Data
public class BankConfig {
    public String baseUrl;
    public String linkMethod;
    public String unLinkMethod;
    public String payMethod;
    public String cashInMethod;
    public String cashOutMethod;
    public String bankName;
}
