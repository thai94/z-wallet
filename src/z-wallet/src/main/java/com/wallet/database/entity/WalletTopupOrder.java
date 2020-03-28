package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallet_topup_order")
@Data
public class WalletTopupOrder {

    @Id
    @Column(name = "order_id")
    public long orderId;
    @Column(name = "amount")
    public long amount;
}
