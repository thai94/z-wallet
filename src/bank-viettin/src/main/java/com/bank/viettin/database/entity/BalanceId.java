package com.bank.viettin.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BalanceId implements Serializable {

    @Column(name = "card_number")
    public String cardNumber;
    @Column(name = "transaction_id")
    public String transactionId;
}
