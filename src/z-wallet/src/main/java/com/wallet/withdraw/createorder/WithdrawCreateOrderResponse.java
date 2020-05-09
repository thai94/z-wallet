package com.wallet.withdraw.createorder;

import com.wallet.entity.BaseResponse;
import lombok.Data;

@Data
public class WithdrawCreateOrderResponse extends BaseResponse {
    public long orderid;
}
