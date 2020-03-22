package com.bank.msb.link;

import com.bank.msb.entity.BaseResponse;
import lombok.Data;

@Data
public class LinkResponse extends BaseResponse {
    public String banktoken;
}
