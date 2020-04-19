package com.wallet.cache.entity;

import com.wallet.constant.ErrorCode;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("MobileCardEntity")
public class MobileCardEntity {

    public long id;
    public String userid;
    public long amount;
    public String cardtype;

    public String cardNumber;
    public String seriNumber;

    public int status = ErrorCode.PROCESSING.getValue();
}
