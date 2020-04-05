package com.bank.sacombank.link;

import com.bank.sacombank.entity.BaseResponse;
import lombok.Data;

@Data
public class LinkResponse extends BaseResponse {
    public String banktoken;
}
