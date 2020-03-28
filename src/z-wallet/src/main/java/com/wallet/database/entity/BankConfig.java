package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_config")
@Data
public class BankConfig {

    @Id
    @Column(name = "bank_code")
    public String bankCode;
    @Column(name = "withdraw_min")
    public long withdrawMin;
    @Column(name = "withdraw_max")
    public long withdrawMax;
    @Column(name = "topup_min")
    public long topupMin;
    @Column(name = "topup_max")
    public long topupMax;
}
