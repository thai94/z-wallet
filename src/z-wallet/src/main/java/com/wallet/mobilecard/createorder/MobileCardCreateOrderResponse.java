package com.wallet.mobilecard.createorder;

import com.wallet.entity.BaseResponse;
import lombok.Data;

@Data
public class MobileCardCreateOrderResponse extends BaseResponse {
    public long orderid;
}
