package com.wallet.mobilecard.orderstatus;

import com.wallet.entity.BaseResponse;
import org.springframework.web.bind.annotation.RestController;

public class MobileCardStatusResponse extends BaseResponse {
    public String cardnumber = "";
    public String serinumber = "";

}
