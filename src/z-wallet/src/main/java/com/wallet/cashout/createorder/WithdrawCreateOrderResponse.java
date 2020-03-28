package com.wallet.cashout.createorder;

import com.wallet.entity.BaseResponse;
import lombok.Data;

@Data
public class WithdrawCreateOrderResponse extends BaseResponse {
    public long orderid;
}
