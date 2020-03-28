package com.wallet.cashin.getstatus;

import com.wallet.cache.entity.WalletTopupEntity;
import com.wallet.cache.repository.WalletTopupCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.entity.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WalletTopupGetStatusController {

    @Autowired
    WalletTopupCacheRepository walletTopupRepository;

    @PostMapping("/wallet-topup/order-status")
    public BaseResponse withdrawStatus(@RequestBody WalletTopupGetStatusRequest request) {
        WalletTopupGetStatusResponse response = new WalletTopupGetStatusResponse();
        try {
            Optional<WalletTopupEntity> orderEntityOptional = walletTopupRepository.findById(request.orderid);
            if(!orderEntityOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_ORDER_NOT_FOUND_ON_CACHE.getValue();
                return response;
            }

            response.returncode = orderEntityOptional.get().getStatus();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
