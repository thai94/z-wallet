package com.wallet.mobilecard.callback;

import com.wallet.cache.entity.MobileCardEntity;
import com.wallet.cache.repository.MobileCardCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.MobileCard;
import com.wallet.database.entity.MobileCardOrder;
import com.wallet.database.repository.MobileCardOrderRepository;
import com.wallet.database.repository.MobileCardRepository;
import com.wallet.entity.BaseResponse;
import com.wallet.mobilecard.createorder.MobileCardCreateOrderResponse;
import com.wallet.moneytransfer.callback.MoneyTransferCallbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MobileCardCallbackController {

    @Autowired
    MobileCardCacheRepository mobileCardCacheRepository;

    @Autowired
    MobileCardRepository mobileCardRepository;

    @Autowired
    MobileCardOrderRepository mobileCardOrderRepository;

    @PostMapping("/mobile-card/callback")
    public BaseResponse callback(@RequestBody MoneyTransferCallbackRequest request) {
        MobileCardCallbackResponse response = new MobileCardCallbackResponse();
        try {

            Optional<MobileCardEntity> cardEntityOptional = mobileCardCacheRepository.findById(Long.valueOf(request.orderid));
            if(!cardEntityOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_ORDER_NOT_FOUND_ON_CACHE.getValue();
                return response;
            }

            MobileCardEntity mobileCardEntity = cardEntityOptional.get();

            // get card number
            MobileCard mobileCard =  mobileCardRepository.findTopOneByStatusAndAmount(1, mobileCardEntity.amount);
            if(mobileCard == null) {
                response.returncode = ErrorCode.CHECK_CARD_NOT_AVALIBLE.getValue();
                mobileCardEntity.status = ErrorCode.CHECK_CARD_NOT_AVALIBLE.getValue();

                mobileCardCacheRepository.save(mobileCardEntity);
                return response;
            }

            mobileCard.status = 2; // đã bán
            mobileCard.orderId = mobileCardEntity.id;
            mobileCardRepository.save(mobileCard);


            mobileCardEntity.status = ErrorCode.SUCCESS.getValue();
            mobileCardEntity.cardNumber = mobileCard.cardNumber;
            mobileCardEntity.seriNumber = mobileCard.seriNumber;
            mobileCardCacheRepository.save(mobileCardEntity);

            MobileCardOrder mobileCardOrder = new MobileCardOrder();
            mobileCardOrder.orderId = mobileCardEntity.id;
            mobileCardOrder.amount = mobileCardEntity.amount;
            mobileCardOrder.cardType = mobileCardEntity.cardtype;
            mobileCardOrder.phone = mobileCardEntity.phone;

            mobileCardOrderRepository.save(mobileCardOrder);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }

    }
}
