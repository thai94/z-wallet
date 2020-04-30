package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_mapping")
@Data
public class BankMapping {

    @EmbeddedId
    public BankMappingId id = new BankMappingId();

    @Column(name = "fulllname")
    public String fulllname;
    @Column(name = "f6cardno")
    public String f6cardno;
    @Column(name = "l4cardno")
    public String l4cardno;
    @Column(name = "bank_token")
    public String bankToken;
    @Column(name = "card_name")
    public String cardName;
    @Column(name = "bank_code")
    public String bankCode;
    @Column(name = "status")
    public int status; // 1. link, 2: unlink
}
