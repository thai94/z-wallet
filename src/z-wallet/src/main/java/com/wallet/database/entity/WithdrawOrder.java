package com.wallet.database.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "withdraw_order")
@Data
public class WithdrawOrder {

    @Id
    @Column(name = "order_id")
    public long orderId;
    @Column(name = "f6cardno")
    public String f6cardno;
    @Column(name = "l4cardno")
    public String l4cardno;
    @Column(name = "amount")
    public long amount;
    @Column(name = "bankcode")
    public String bankcode;
}
