package com.wallet.database.entity;

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
public class BankMappingId implements Serializable {
    @Column(name = "userid")
    public String userid;
    @Column(name = "card_number")
    public String cardNumber;
}
