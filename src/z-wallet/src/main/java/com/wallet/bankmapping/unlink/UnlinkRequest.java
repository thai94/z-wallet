package com.wallet.bankmapping.unlink;

import lombok.Data;

@Data
public class UnlinkRequest {
    public String userid;
    public String f6cardno;
    public String l4cardno;
    public String bankcode;
}
