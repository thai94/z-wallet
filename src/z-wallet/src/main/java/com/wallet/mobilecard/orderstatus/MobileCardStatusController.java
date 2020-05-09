package com.wallet.mobilecard.orderstatus;

import com.wallet.cache.entity.MobileCardEntity;
import com.wallet.cache.repository.MobileCardCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MobileCardStatusController {

    @Autowired
    MobileCardCacheRepository mobileCardCacheRepository;

    @PostMapping("/mobile-card/order-status")
    public BaseResponse getOrderStatus(@RequestBody MobileCardStatusRequest request) {
        MobileCardStatusResponse response = new MobileCardStatusResponse();
        try {
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(request.orderid <= 0) {
                response.returncode = ErrorCode.VALIDATE_ORDER_ID_INVALID.getValue();
                return response;
            }

            Optional<MobileCardEntity> mobileCardEntityOptional = mobileCardCacheRepository.findById(request.orderid);
            if(!mobileCardEntityOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_ORDER_NOT_FOUND_ON_CACHE.getValue();
                return response;
            }

            response.returncode = mobileCardEntityOptional.get().status;
            response.cardnumber = mobileCardEntityOptional.get().cardNumber;
            response.serinumber =  mobileCardEntityOptional.get().seriNumber;

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
