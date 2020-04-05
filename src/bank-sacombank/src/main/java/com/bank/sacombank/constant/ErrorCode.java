package com.bank.sacombank.constant;

public enum ErrorCode {

    SUCCESS(1),
    EXCEPTION(0),
    // -1 -> -100 validate
    VALIDATE_BANK_TOKEN_INVALID(-1),
    VALIDATE_CARD_NUMBER_INVALID(-2),
    VALIDATE_TRANSACTION_ID_INVALID(-4),
    VALIDATE_AMOUNT_INVALID(-5),
    VALIDATE_CMND_INVALID(-6),
    VALIDATE_FULLNAME_INVALID(-7),
    VALIDATE_PHONE_INVALID(-8),

    // -101 -> -200
    BUZ_CARD_NUMBER_NOT_MAPPING(-101),
    BUZ_BANK_TOKEN_WRONG(-102),
    BUZ_BALANCE_NOT_ENOUGHT(-103),
    BUZ_TRANSACTION_DUPLICATE(-104),
    BUZ_CUSTOMER_NOT_FOUND(-105),
    BUZ_CARD_NUMBER_NOT_FOUND(-106),
    BUZ_CARD_NUMBER_ALREADY_LINKED(-107),
    BUZ_CARD_NUMBER_ALREADY_UN_LINKED(-108);

    private int value;
    ErrorCode(int value){
        this.value=value;
    }

    public int getValue() {
        return this.value;
    }
}