package com.wallet.user.login;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    public String phone;
    public String pin;
}
