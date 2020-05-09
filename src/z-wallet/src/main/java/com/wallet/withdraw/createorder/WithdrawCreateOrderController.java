package com.wallet.withdraw.createorder;

import com.wallet.cache.entity.WithdrawOrderEntity;
import com.wallet.cache.repository.WithdrawOrderCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.BankConfig;
import com.wallet.database.entity.BankMapping;
import com.wallet.database.repository.BankConfigRespository;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.utils.GenId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WithdrawCreateOrderController {

    @Autowired
    WithdrawOrderCacheRepository withdrawOrderRepository;

    @Autowired
    BankConfigRespository bankConfigRespository;

    @Autowired
    BankMappingRespository bankMappingRespository;

    @PostMapping("/withdraw/create-order")
    public BaseResponse createOrder(@RequestBody WithdrawCreateOrderRequest request) {
        WithdrawCreateOrderResponse response = new WithdrawCreateOrderResponse();
        try {
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.f6cardno)) {
                response.returncode = ErrorCode.VALIDATE_F6CARD_NO_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.l4cardno)) {
                response.returncode = ErrorCode.VALIDATE_L4CARD_NO_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.bankcode)) {
                response.returncode = ErrorCode.VALIDATE_BANK_CODE_INVALID.getValue();
                return response;
            }

            if(request.amount <= 0) {
                response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
                return response;
            }

            Optional<BankConfig> bankConfigOptional = bankConfigRespository.findById(request.bankcode);
            if(!bankConfigOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_BANK_NOT_SUPPORT.getValue();
                return response;
            }

            BankConfig bankConfig = bankConfigOptional.get();
            if(request.amount < bankConfig.withdrawMin || request.amount > bankConfig.withdrawMax) {
                response.returncode = ErrorCode.CHECK_AMOUNT_LIMITATION.getValue();
                return response;
            }

            BankMapping bankMapping = bankMappingRespository.findOneByIdUseridAndF6cardnoAndL4cardno(request.userid, request.f6cardno, request.l4cardno);
            if(bankMapping == null) {
                response.returncode = ErrorCode.USER_HAS_NOT_MAPPING_YET.getValue();
                return response;
            }

            response.orderid = Long.valueOf(GenId.genId());

            WithdrawOrderEntity entity = new WithdrawOrderEntity();
            entity.userid = request.userid;
            entity.f6cardno = request.f6cardno;
            entity.l4cardno = request.l4cardno;
            entity.bankcode = request.bankcode;
            entity.amount = request.amount;
            entity.id = String.valueOf(response.orderid);
            entity.status = ErrorCode.PROCESSING.getValue();

            withdrawOrderRepository.save(entity);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
