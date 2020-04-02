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
public class WalletId implements Serializable {

    @Column(name = "user_id")
    public String userId;
    @Column(name = "transaction_id")
    public long transactionId;
}
