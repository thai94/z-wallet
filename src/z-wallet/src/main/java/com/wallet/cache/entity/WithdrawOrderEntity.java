package com.wallet.cache.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@RedisHash("WithdrawOrderEntity")
public class WithdrawOrderEntity implements Serializable {

    @Id
    public String id;
    public String userid;
    public String f6cardno;
    public String l4cardno;
    public String bankcode;
    public long amount;
    public int status = 0;
}
