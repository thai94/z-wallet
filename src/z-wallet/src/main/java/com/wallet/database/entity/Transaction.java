package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @Column(name = "transaction_Id")
    public long transactionId;
    @Column(name = "order_id")
    public long orderId;
    @Column(name = "user_id")
    public String userId;
    @Column(name = "source_of_fund")
    public int sourceOfFund;
    @Column(name = "charge_time")
    public long chargeTime;
    @Column(name = "amount")
    public long amount;
    @Column(name = "status")
    public int status;
    @Column(name = "service_type")
    public int serviceType;
}
