package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "wallet_user")
@Data
public class WalletUser {
    @Id
    @Column(name = "user_id")
    public String userId;
    @Column(name = "password")
    public String password;
    @Column(name = "phone")
    public String phone;
    @Column(name = "address")
    public String address;
    @Column(name = "dob")
    public Date dob;
    @Column(name = "full_name")
    public String fullName;
    @Column(name = "cmnd")
    public String cmnd;
    @Column(name = "pin")
    public String pin;
}
