package com.wallet.bankmapping.supportbank;

import com.wallet.constant.ErrorCode;
import com.wallet.constant.SupportBank;
import com.wallet.entity.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetSupportBankController {

    @PostMapping("/bank-mapping/support-bank")
    public BaseResponse supportBank(@RequestBody GetSupportBankRequest request) {
        GetSupportBankResponse response = new GetSupportBankResponse();
        try {
            BankInfo bankInfo = null;
            for(SupportBank supportBank: SupportBank.values()) {
                bankInfo = new BankInfo();
                bankInfo.bankcode = supportBank.getBankCode();
                bankInfo.bankname = supportBank.getBankName();

                response.banks.add(bankInfo);
            }

            response.returncode = ErrorCode.SUCCESS.getValue();
            return  response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
