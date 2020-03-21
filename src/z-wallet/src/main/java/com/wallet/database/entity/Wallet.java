package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "z_wallet")
@Data
public class Wallet {

    @EmbeddedId
    public WalletId id = new WalletId();
    public long balance;
    @Column(name = "update_date")
    public Timestamp updateDate;
}
