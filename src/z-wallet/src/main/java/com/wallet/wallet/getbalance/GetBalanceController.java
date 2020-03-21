package com.wallet.wallet.getbalance;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.Wallet;
import com.wallet.database.repository.WalletRepository;
import com.wallet.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBalanceController {

    @Autowired
    WalletRepository walletRepository;

    @PostMapping("/wallet/get-balance")
    public BaseResponse getBalance(@RequestBody GetBalanceRequest request) {
        GetBalanceResponse response = new GetBalanceResponse();
        try {

            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            Wallet wallet = walletRepository.findTopByIdUserIdOrderByUpdateDateDesc(request.userid);
            if(wallet == null) {
                response.amount = 0;
                response.returncode = ErrorCode.SUCCESS.getValue();
                return response;
            }

            response.amount = wallet.balance;
            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch(Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
