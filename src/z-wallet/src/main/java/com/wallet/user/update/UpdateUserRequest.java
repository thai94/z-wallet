package com.wallet.user.update;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String userid;
    public String pin;
    public String cmnd;
    public String address;
    public String dob;
}
