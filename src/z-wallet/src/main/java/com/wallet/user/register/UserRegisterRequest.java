package com.wallet.user.register;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    public String fullname;
    public String pin; // HMAC26 and encrypted by key K
    public String phone;
    public String key; // Key K is encrypted by RSA
}
