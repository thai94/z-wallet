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
public class BankCardId implements Serializable {

    @Column(name = "cmnd")
    public String cmnd;
    @Column(name = "card_number")
    public String cardNumber;
}
