package com.wallet.mobilecard.createorder;

import com.wallet.cache.entity.MobileCardEntity;
import com.wallet.cache.repository.MobileCardCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.MobileCard;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.MobileCardRepository;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.utils.GenId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MobileCardCreateOrderController {

    @Autowired
    WalletUserRespository walletUserRespository;

    @Autowired
    MobileCardCacheRepository mobileCardCacheRepository;

    @Autowired
    MobileCardRepository mobileCardRepository;

    @PostMapping("/mobile-card/create-order")
    public BaseResponse createOrder(@RequestBody MobileCardCreateOrderRequest request) {
        MobileCardCreateOrderResponse response = new MobileCardCreateOrderResponse();
        try {
            // validate
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(request.amount <= 0) {
                response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.phone)) {
                response.returncode = ErrorCode.VALIDATE_PHONE_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.cardtype)) {
                response.returncode = ErrorCode.VALIDATE_CARD_TYPE_INVALID.getValue();
                return response;
            }

            if(!request.cardtype.equals("VT") && !request.cardtype.equals("VINA") && !request.cardtype.equals("MB")) {
                response.returncode = ErrorCode.VALIDATE_CARD_TYPE_INVALID.getValue();
                return response;
            }

            Optional<WalletUser> walletUserOpt = walletUserRespository.findById(request.userid);
            if(!walletUserOpt.isPresent()) {
                response.returncode = ErrorCode.CHECK_USER_DOES_NOT_EXIST.getValue();
                return response;
            }

            MobileCard mobileCard =  mobileCardRepository.findTopOneByStatusAndAmount(1, request.amount);
            if(mobileCard == null) {
                response.returncode = ErrorCode.CHECK_CARD_NOT_AVALIBLE.getValue();
                return response;
            }

            MobileCardEntity mobileCardEntity = new MobileCardEntity();
            mobileCardEntity.id = Long.valueOf(GenId.genId());
            mobileCardEntity.userid = request.userid;
            mobileCardEntity.amount = request.amount;
            mobileCardEntity.phone = request.phone;
            mobileCardEntity.cardtype = request.cardtype;
            mobileCardEntity.status = ErrorCode.PROCESSING.getValue();

            mobileCardCacheRepository.save(mobileCardEntity);

            response.returncode = ErrorCode.SUCCESS.getValue();
            response.orderid = mobileCardEntity.id;
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
