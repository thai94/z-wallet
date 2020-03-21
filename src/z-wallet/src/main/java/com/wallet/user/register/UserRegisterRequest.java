package com.wallet.user.register;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserRegisterRequest implements Serializable {
    public String fullname;
    public String password;
    public String phone;
}
