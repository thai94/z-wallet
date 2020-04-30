package com.wallet.admin.user.verify;

import lombok.Data;

@Data
public class VerifyUserRequest {

    public String userid = "";
    public int status = 2; // 1: verified, 2: rejected
    public String comment = "";
}
