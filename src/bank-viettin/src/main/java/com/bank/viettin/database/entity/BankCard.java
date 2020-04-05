package com.bank.viettin.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "bank_card")
@Data
public class BankCard implements Serializable {

    @EmbeddedId
    public BankCardId id;

    @Column(name = "register_date")
    public Timestamp registerDate;
    @Column(name = "type")
    public String type;
}
