package com.wallet.constant;

public enum  ErrorCode {

    SUCCESS(1),
    EXCEPTION(0),
    // -1 -> -100 validate
    VALIDATE_FULL_NAME_INVALID(-1),
    VALIDATE_PIN_INVALID(-2),
    VALIDATE_PHONE_INVALID(-2),
    VALIDATE_PHONE_DUPLICATE(-3),

    // -101 -> -200 BUS
    USER_PASSWORD_WRONG(-101);

    private int value;
    ErrorCode(int value){
        this.value=value;
    }

    public int getValue() {
        return this.value;
    }
}
