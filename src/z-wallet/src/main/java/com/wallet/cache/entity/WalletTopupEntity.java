package com.wallet.cache.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("WalletTopupEntity")
public class WalletTopupEntity {
    public String id;
    public String userid;
    public long amount;
    public int status;
}
