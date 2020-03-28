package com.wallet.moneytransfer.getorderstatus;

import com.wallet.cache.entity.MoneyTransferEntity;
import com.wallet.cache.repository.MoneyTransferCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MoneyTransferGetOrderStatusController {

    @Autowired
    MoneyTransferCacheRepository moneyTransferCacheRepository;

    @PostMapping("/p2p-transfer/order-status")
    public BaseResponse getOrderStatus(@RequestBody MoneyTransferGetOrderStatusRequest request) {
        MoneyTransferGetOrderStatusResponse response = new MoneyTransferGetOrderStatusResponse();
        try {
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.orderid)) {
                response.returncode = ErrorCode.VALIDATE_ORDER_ID_INVALID.getValue();
                return response;
            }

            Optional<MoneyTransferEntity> moneyTransferEntityOptional = moneyTransferCacheRepository.findById(request.orderid);
            if(!moneyTransferEntityOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_ORDER_NOT_FOUND_ON_CACHE.getValue();
                return response;
            }

            response.returncode = moneyTransferEntityOptional.get().getStatus();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
