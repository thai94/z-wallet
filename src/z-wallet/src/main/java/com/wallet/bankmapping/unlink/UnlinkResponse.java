package com.wallet.bankmapping.unlink;

import com.wallet.entity.BaseResponse;
import lombok.Data;

@Data
public class UnlinkResponse extends BaseResponse {
    public int bankreturncode;
}
