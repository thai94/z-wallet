package com.wallet.moneytransfer.createorder;

import com.wallet.cache.entity.MoneyTransferEntity;
import com.wallet.cache.repository.MoneyTransferCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.utils.GenId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoneyTransferCreateOrderController {

    @Autowired
    MoneyTransferCacheRepository moneyTransferCacheRepository;

    @Autowired
    WalletUserRespository walletUserRespository;

    @PostMapping("/p2p-transfer/create-order")
    public BaseResponse createOrder(@RequestBody MoneyTransferCreateOrderRequest request) {
        MoneyTransferCreateOrderResponse response = new MoneyTransferCreateOrderResponse();
        try {
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(request.amount <= 0) {
                response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.receiverphone)) {
                response.returncode = ErrorCode.VALIDATE_PHONE_INVALID.getValue();
                return response;
            }

            WalletUser walletUser = walletUserRespository.findWalletUserByPhone(request.receiverphone);
            if(walletUser == null) {
                response.returncode = ErrorCode.CHECK_USER_DOES_NOT_EXIST.getValue();
                return response;
            }

            response.orderid = Long.valueOf(GenId.genId());

            MoneyTransferEntity moneyTransferEntity = new MoneyTransferEntity();
            moneyTransferEntity.id = String.valueOf(response.orderid);
            moneyTransferEntity.userid = request.userid;
            moneyTransferEntity.amount = request.amount;
            moneyTransferEntity.receiverphone = request.receiverphone;
            moneyTransferEntity.status = ErrorCode.PROCESSING.getValue();

            moneyTransferCacheRepository.save(moneyTransferEntity);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }

    }
}
