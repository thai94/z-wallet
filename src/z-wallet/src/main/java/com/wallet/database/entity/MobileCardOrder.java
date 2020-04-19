package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mobile_card_order")
@Data
public class MobileCardOrder {

    @Id
    @Column(name = "order_id")
    public long orderId;
    @Column(name = "amount")
    public long amount;
    @Column(name = "card_type")
    public String cardType;
}
