package com.wallet.constant;

public enum  ErrorCode {

    SUCCESS(1),
    EXCEPTION(0),
    // -1 -> -100 validate
    VALIDATE_FULL_NAME_INVALID(-1),
    VALIDATE_PIN_INVALID(-2),
    VALIDATE_PHONE_INVALID(-3),
    VALIDATE_PHONE_DUPLICATE(-4),
    VALIDATE_USER_ID_INVALID(-5),
    VALIDATE_AMOUNT_INVALID(-6),
    VALIDATE_TRANSACTION_ID_INVALID(-7),
    VALIDATE_TRANSACTION_DUPLICATE(-8),

    // -101 -> -200 BUS
    USER_PASSWORD_WRONG(-101),
    USER_PIN_WRONG(-102),
    BALANCE_NOT_ENOUGHT(-103);

    private int value;
    ErrorCode(int value){
        this.value=value;
    }

    public int getValue() {
        return this.value;
    }
}