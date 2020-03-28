package com.wallet.cashout.getstatus;

import com.wallet.cache.entity.WithdrawOrderEntity;
import com.wallet.cache.repository.WithdrawOrderCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.entity.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WithdrawGetStatusController {

    @Autowired
    WithdrawOrderCacheRepository withdrawOrderRepository;

    @PostMapping("/withdraw/order-status")
    public BaseResponse withdrawStatus(@RequestBody WithdrawGetStatusRequest request) {
        WithdrawGetStatusResponse response = new WithdrawGetStatusResponse();
        try {
            Optional<WithdrawOrderEntity> orderEntityOptional = withdrawOrderRepository.findById(request.orderid);
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
