package com.wallet.user.update;

import lombok.Data;

@Data
public class UpdateUserRequest {

    // key
    public String userid;

    // update
    public String pin;
    public String cmnd;
    public String address;
    public String dob;

    public String cmndfontimg; // file-id
    public String cmndbackimg; // file-id
    public String avatar; // file-id
}
