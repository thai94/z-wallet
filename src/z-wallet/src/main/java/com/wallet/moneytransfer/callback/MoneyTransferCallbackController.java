package com.wallet.moneytransfer.callback;

import com.wallet.cache.entity.MoneyTransferEntity;
import com.wallet.cache.repository.MoneyTransferCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.MoneyTransferOrder;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.MoneyTransferOrderRepository;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.properties.WalletConfig;
import com.wallet.wallet.addcash.AddCashRequest;
import com.wallet.wallet.addcash.AddCashResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class MoneyTransferCallbackController {

    @Autowired
    MoneyTransferCacheRepository moneyTransferCacheRepository;

    @Autowired
    WalletUserRespository walletUserRespository;

    @Autowired
    MoneyTransferOrderRepository moneyTransferOrderRepository;

    @Autowired
    WalletConfig walletConfig;

    @PostMapping("/p2p-transfer/callback")
    public BaseResponse callback(@RequestBody MoneyTransferCallbackRequest request) {
        MoneyTransferCallbackResponse response = new MoneyTransferCallbackResponse();
        try {
            Optional<MoneyTransferEntity> moneyTransferEntityOptional = moneyTransferCacheRepository.findById(request.orderid);
            if(!moneyTransferEntityOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_ORDER_NOT_FOUND_ON_CACHE.getValue();
                return response;
            }
            MoneyTransferEntity moneyTransferEntity = moneyTransferEntityOptional.get();

            WalletUser walletUser = walletUserRespository.findWalletUserByPhone(moneyTransferEntity.getReceiverphone());

            AddCashRequest addCashRequest = new AddCashRequest();
            addCashRequest.userid = walletUser.userId;
            addCashRequest.amount = moneyTransferEntity.getAmount();
            addCashRequest.transactionid = Long.valueOf(request.transactionid);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<AddCashResponse> responseEntity = restTemplate.postForEntity(walletConfig.baseUrl + walletConfig.addCashMethod, addCashRequest, AddCashResponse.class);
            if(responseEntity.getStatusCode() != HttpStatus.OK) {

                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            moneyTransferEntity.status = responseEntity.getBody().returncode;
            moneyTransferCacheRepository.save(moneyTransferEntity);

            if(responseEntity.getBody().returncode != 1) {
                response.returncode = responseEntity.getBody().returncode;
                return response;
            }

            // database
            MoneyTransferOrder moneyTransferOrder = new MoneyTransferOrder();
            moneyTransferOrder.orderId = Long.valueOf(request.orderid);
            moneyTransferOrder.amount = moneyTransferEntity.amount;
            moneyTransferOrder.receiverPhone = moneyTransferEntity.receiverphone;

            moneyTransferOrderRepository.save(moneyTransferOrder);

            response.returncode = responseEntity.getBody().returncode;
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }

    }
}
