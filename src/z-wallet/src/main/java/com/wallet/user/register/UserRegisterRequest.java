package com.wallet.user.register;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    public String fullname;
    public String pin;
    public String phone;
}
