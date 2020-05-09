package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallet_user")
@Data
public class WalletUser {
    @Id
    @Column(name = "user_id")
    public String userId;
    @Column(name = "phone")
    public String phone;
    @Column(name = "address")
    public String address;
    @Column(name = "dob")
    public String dob;
    @Column(name = "full_name")
    public String fullName;
    @Column(name = "cmnd")
    public String cmnd;
    @Column(name = "pin")
    public String pin;

    public String cmndFontImg; // file-id
    public String cmndBackImg; // file-id
    public String avatar; // file-id

    public int verify = 0; // 0: not verified yet, 1: verified, 2: rejected
}
