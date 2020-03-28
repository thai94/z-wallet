package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "money_transfer_order")
@Data
public class MoneyTransferOrder {

    @Id
    @Column(name = "order_id")
    public long orderId;
    @Column(name = "amount")
    public long amount;
    @Column(name = "receiver_phone")
    public String receiverPhone;
}
