package com.wallet.user.login;

import com.wallet.entity.BaseResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    public String phone;
    public String pin;
}
