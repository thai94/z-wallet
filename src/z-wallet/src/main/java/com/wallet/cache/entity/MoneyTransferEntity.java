package com.wallet.cache.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@RedisHash("MoneyTransferEntity")
public class MoneyTransferEntity {
    @Id
    public String id;
    public String userid;
    public long amount;
    public String receiverphone;
    public int status;
}
