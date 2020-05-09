package com.wallet.wallettopup.createorder;

import com.wallet.cache.entity.WalletTopupEntity;
import com.wallet.cache.repository.WalletTopupCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.database.repository.BankConfigRespository;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.utils.GenId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletTopupCreateOrderController {

    @Autowired
    BankConfigRespository bankConfigRespository;

    @Autowired
    BankMappingRespository bankMappingRespository;

    @Autowired
    WalletTopupCacheRepository walletTopupRepository;

    @PostMapping("/wallet-topup/create-order")
    public BaseResponse createOrder(@RequestBody WalletTopupCreateOrderRequest request) {
        WalletTopupCreateOrderResponse response = new WalletTopupCreateOrderResponse();
        try {
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(request.amount <= 0) {
                response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
                return response;
            }

            response.orderid = Long.valueOf(GenId.genId());

            WalletTopupEntity walletTopupEntity = new WalletTopupEntity();
            walletTopupEntity.id = String.valueOf(response.orderid);
            walletTopupEntity.userid = request.userid;
            walletTopupEntity.amount = request.amount;
            walletTopupEntity.status = ErrorCode.PROCESSING.getValue();

            walletTopupRepository.save(walletTopupEntity);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
