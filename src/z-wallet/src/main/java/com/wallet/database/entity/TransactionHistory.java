package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_history")
@Data
public class TransactionHistory {

    @EmbeddedId
    public TransactionHistoryId id= new TransactionHistoryId();

    public long orderId;
    public long amount;
    public int serviceType;
    public int sourceofFund;
    public long timemilliseconds;
    public String txndetail;
    public int status = 2; // 1: Thanh cong. 2: Dang xu li, -1 that bai
}
