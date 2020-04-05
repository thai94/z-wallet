package com.wallet.constant;

import lombok.Getter;

@Getter
public enum SupportBank {

    MSB("msb","MSB"),
    VIET_TIN_BANK("vtb","Viet Tin Bank"),
    SACOM_BANK("scb","Cacombank");

    private String bankCode;
    private String bankName;

    SupportBank(String bankCode, String bankName){
        this.bankCode = bankCode;
        this.bankName = bankName;
    }
}
