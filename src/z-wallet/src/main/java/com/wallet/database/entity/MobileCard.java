package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mobile_card")
@Data
public class MobileCard {

    @Id
    @Column(name = "card_number")
    public String cardNumber;
    @Column(name = "seri_number")
    public String seriNumber;
    @Column(name = "card_type")
    public String  cardType;
    @Column(name = "order_id")
    public long orderId;
    @Column(name = "status")
    public int status;
    @Column(name = "amount")
    public long amount;
}
