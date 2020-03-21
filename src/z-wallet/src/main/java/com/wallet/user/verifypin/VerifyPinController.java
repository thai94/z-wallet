package com.wallet.user.verifypin;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyPinController {

    @Autowired
    WalletUserRespository walletUserRespository;

    @PostMapping("/um/verify-pin")
    public BaseResponse verifypin(@RequestBody VerifyPinRequest verifyPinRequest) {
        VerifyPinResponse response = new VerifyPinResponse();

        try {
            if(StringUtils.isEmpty(verifyPinRequest.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(verifyPinRequest.pin)) {
                response.returncode = ErrorCode.VALIDATE_PIN_INVALID.getValue();
                return response;
            }

            WalletUser user = walletUserRespository.findWalletUserByUserIdAndPin(verifyPinRequest.userid, verifyPinRequest.pin);
            if(user == null) {
                response.returncode = ErrorCode.USER_PIN_WRONG.getValue();
                return response;
            }

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
