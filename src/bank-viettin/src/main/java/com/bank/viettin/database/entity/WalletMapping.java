package com.bank.viettin.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "wallet_mapping")
@Data
public class WalletMapping implements Serializable {

    @Id
    @Column(name = "card_number")
    public String cardNumber;
    @Column(name = "bank_token")
    public String bankToken;
    @Column(name = "mapping_date")
    public Timestamp mappingDate;
    @Column(name = "status")
    public int status;
}
