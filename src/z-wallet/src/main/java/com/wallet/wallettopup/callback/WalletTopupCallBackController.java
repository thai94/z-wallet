package com.wallet.wallettopup.callback;

import com.wallet.cache.entity.WalletTopupEntity;
import com.wallet.cache.repository.WalletTopupCacheRepository;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.TransactionHistory;
import com.wallet.database.entity.TransactionHistoryId;
import com.wallet.database.entity.WalletTopupOrder;
import com.wallet.database.repository.TransactionHistoryRepository;
import com.wallet.database.repository.WalletTopupOrderRepository;
import com.wallet.entity.BaseResponse;
import com.wallet.properties.WalletProperties;
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
public class WalletTopupCallBackController {

    @Autowired
    WalletTopupCacheRepository walletTopupRepository;
    @Autowired
    WalletProperties walletConfig;
    @Autowired
    WalletTopupOrderRepository walletTopupOrderRepository;
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @PostMapping("/wallet-topup/callback")
    public BaseResponse callback(@RequestBody WalletTopupCallRequest request) {
        WalletTopupCallResponse response = new WalletTopupCallResponse();
        try {
            Optional<WalletTopupEntity> walletTopupEntityOptional = walletTopupRepository.findById(request.orderid);
            if(!walletTopupEntityOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_ORDER_NOT_FOUND_ON_CACHE.getValue();
                return response;
            }

            WalletTopupEntity walletTopupEntity = walletTopupEntityOptional.get();

            AddCashRequest addCashRequest = new AddCashRequest();
            addCashRequest.userid = walletTopupEntity.userid;
            addCashRequest.amount = walletTopupEntity.getAmount();
            addCashRequest.transactionid = request.transactionid;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<AddCashResponse> responseEntity = restTemplate.postForEntity(walletConfig.baseUrl + walletConfig.addCashMethod, addCashRequest, AddCashResponse.class);
            if(responseEntity.getStatusCode() != HttpStatus.OK) {

                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            // update cache
            walletTopupEntity.status = responseEntity.getBody().returncode;
            walletTopupRepository.save(walletTopupEntity);

            if(responseEntity.getBody().returncode != 1) {

                // TransactionHistory
                TransactionHistoryId transactionHistoryId = new TransactionHistoryId(request.userid, request.transactionid);
                Optional<TransactionHistory> transactionHistoryOptional = transactionHistoryRepository.findById(transactionHistoryId);
                if(!transactionHistoryOptional.isPresent()) {
                    response.returncode = responseEntity.getBody().returncode;
                    return response;
                }

                TransactionHistory transactionHistory = transactionHistoryOptional.get();
                transactionHistory.orderId = Long.valueOf(walletTopupEntity.id);
                transactionHistory.status = -1; // that bai

                transactionHistoryRepository.save(transactionHistory);

                response.returncode = responseEntity.getBody().returncode;
                return response;
            }
            // database
            // WalletTopupOrder
            WalletTopupOrder walletTopupOrder = new WalletTopupOrder();
            walletTopupOrder.orderId = Long.valueOf(walletTopupEntity.id);
            walletTopupOrder.amount = walletTopupEntity.getAmount();
            walletTopupOrderRepository.save(walletTopupOrder);

            // TransactionHistory
            TransactionHistoryId transactionHistoryId = new TransactionHistoryId(request.userid, request.transactionid);

            Optional<TransactionHistory> transactionHistoryOptional = transactionHistoryRepository.findById(transactionHistoryId);
            if(!transactionHistoryOptional.isPresent()) {
                response.returncode = responseEntity.getBody().returncode;
                return response;
            }

            TransactionHistory transactionHistory = transactionHistoryOptional.get();
            transactionHistory.orderId = Long.valueOf(walletTopupEntity.id);
            transactionHistory.status = 1; // thanh cong

            transactionHistoryRepository.save(transactionHistory);

            response.returncode = responseEntity.getBody().returncode;
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
