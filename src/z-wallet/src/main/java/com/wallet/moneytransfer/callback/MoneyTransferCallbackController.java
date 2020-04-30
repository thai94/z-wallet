package com.wallet.moneytransfer.callback;

import com.wallet.cache.entity.MoneyTransferEntity;
import com.wallet.cache.repository.MoneyTransferCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.constant.Service;
import com.wallet.database.entity.MoneyTransferOrder;
import com.wallet.database.entity.UserNotify;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.MoneyTransferOrderRepository;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.notify.send.SendNotifyService;
import com.wallet.properties.WalletProperties;
import com.wallet.utils.GsonUtils;
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
    WalletProperties walletConfig;

    @Autowired
    SendNotifyService sendNotifyService;

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

            WalletUser receiverWalletUser = walletUserRespository.findWalletUserByPhone(moneyTransferEntity.getReceiverphone());

            AddCashRequest addCashRequest = new AddCashRequest();
            addCashRequest.userid = receiverWalletUser.userId;
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

            // fail
            if(responseEntity.getBody().returncode != 1) {
                response.returncode = responseEntity.getBody().returncode;
                return response;
            }

            // success
            // save database
            MoneyTransferOrder moneyTransferOrder = new MoneyTransferOrder();
            moneyTransferOrder.orderId = Long.valueOf(request.orderid);
            moneyTransferOrder.amount = moneyTransferEntity.amount;
            moneyTransferOrder.receiverPhone = moneyTransferEntity.receiverphone;

            moneyTransferOrderRepository.save(moneyTransferOrder);

            response.returncode = responseEntity.getBody().returncode;

            // notify

            UserNotify notify = new UserNotify();
            notify.notifyId = System.currentTimeMillis();
            notify.userId = receiverWalletUser.userId;;
            notify.serviceType = Service.MONEY_TRANSFER.getKey();

            Optional<WalletUser> walletUserOptional = walletUserRespository.findById(request.userid);
            WalletUser sendWalletUser = walletUserOptional.get();

            notify.title = String.format("Nhận tiền từ %s", sendWalletUser.fullName);

            MoneyTransferNotifyTemplate moneyTransferNotifyTemplate = new MoneyTransferNotifyTemplate();
            moneyTransferNotifyTemplate.amount = moneyTransferEntity.amount;
            moneyTransferNotifyTemplate.status = 1;
            moneyTransferNotifyTemplate.orderid = request.orderid;
            moneyTransferNotifyTemplate.transactionid = request.transactionid;
            moneyTransferNotifyTemplate.sender = sendWalletUser.fullName;

            notify.content = GsonUtils.toJsonString(moneyTransferNotifyTemplate);
            notify.status = 1; // new
            notify.createDate = System.currentTimeMillis();
            sendNotifyService.send(notify);

            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }

    }
}
