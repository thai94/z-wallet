package com.wallet.user.login;

import com.wallet.entity.BaseResponse;
import lombok.Data;

@Data
public class LoginResponse extends BaseResponse {
    public String userid = "";
    public String phone = "";
    public String address = "";
    public String dob;
    public String fullname = "";
    public String cmnd;

    public String cmndFontImg; // file-id
    public String cmndBackImg; // file-id
    public String avatar; // file-id

    public int verify = 0; // 0: not verified yet, 1: verified, 2: rejected
}
