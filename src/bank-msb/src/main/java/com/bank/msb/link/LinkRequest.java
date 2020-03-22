package com.bank.msb.link;

import lombok.Data;

@Data
public class LinkRequest {
    public String cardnumber;
    public String cmnd;
    public String fullname;
    public String phone;
}
