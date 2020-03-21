package com.wallet.constant;

public enum  ErrorCode {

    SUCCESS(1),
    EXCEPTION(0),
    // -1 -> -100 validate
    VALIDATE_FULL_NAME_INVALID(-1),
    VALIDATE_PASSWORD_INVALID(-2),
    VALIDATE_PHONE_INVALID(-2),
    VALIDATE_PHONE_DUPLICATE(-3),

    // -101 -> -200 BUS
    BUZ_FAIL_CAN_NOT_SEND_OTP(-101);

    private int value;
    ErrorCode(int value){
        this.value=value;
    }

    public int getValue() {
        return this.value;
    }
}
