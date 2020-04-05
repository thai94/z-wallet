package com.bank.sacombank.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "balance")
@Data
public class Balance implements Serializable {
    @EmbeddedId
    public BalanceId id = new BalanceId();

    @Column(name = "balance")
    public Long balance;
    @Column(name = "update_date")
    public Timestamp updateDate;
}
