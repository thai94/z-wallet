package com.wallet.wallet.getbalance;

import com.wallet.entity.BaseResponse;
import lombok.Data;

@Data
public class GetBalanceResponse extends BaseResponse {
    public long amount;
}
