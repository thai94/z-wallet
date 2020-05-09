package com.wallet.withdraw.callback;

import com.wallet.cache.entity.WithdrawOrderEntity;
import com.wallet.cache.repository.WithdrawOrderCacheRepository;
import com.wallet.configuration.SecureHttpConverter;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.BankMapping;
import com.wallet.database.entity.TransactionHistory;
import com.wallet.database.entity.TransactionHistoryId;
import com.wallet.database.entity.WithdrawOrder;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.database.repository.TransactionHistoryRepository;
import com.wallet.database.repository.WithdrawOrderRepository;
import com.wallet.entity.BaseResponse;
import com.wallet.properties.BankConfig;
import com.wallet.properties.BankProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WithdrawCallbackController {

    @Autowired
    BankProperties bankProperties;

    @Autowired
    WithdrawOrderCacheRepository withdrawOrderCacheRepository;

    @Autowired
    BankMappingRespository bankMappingRespository;

    @Autowired
    WithdrawOrderRepository withdrawOrderRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @PostMapping("/withdraw/callback")
    public BaseResponse callback(@RequestBody WithdrawCallbackRequest request) {
        WithdrawCallbackResponse response = new WithdrawCallbackResponse();
        try {
            Optional<WithdrawOrderEntity> orderEntityOptional = withdrawOrderCacheRepository.findById(request.orderid);
            if(!orderEntityOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_ORDER_NOT_FOUND_ON_CACHE.getValue();
                return response;
            }

            WithdrawOrderEntity entity = orderEntityOptional.get();

            BankMapping bankMapping = bankMappingRespository.findOneByIdUseridAndF6cardnoAndL4cardno(entity.userid, entity.f6cardno, entity.l4cardno);
            BankCashInRequest bankCashInRequest = new BankCashInRequest();
            bankCashInRequest.banktoken = bankMapping.bankToken;
            bankCashInRequest.cardnumber = bankMapping.id.cardNumber;
            bankCashInRequest.transactionid = request.transactionid;
            bankCashInRequest.amount = entity.amount;

            BankConfig bankConfig = bankProperties.getConnector().get(entity.bankcode);
            if(bankConfig == null) {
                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> converterList = new ArrayList<>();
            converterList.add(new SecureHttpConverter(bankConfig.secretKey, bankConfig.salt));
            restTemplate.setMessageConverters(converterList);
            ResponseEntity<BankCashInResponse> responseEntity = restTemplate.postForEntity(bankConfig.baseUrl + bankConfig.cashInMethod, bankCashInRequest, BankCashInResponse.class);
            if(responseEntity.getStatusCode() != HttpStatus.OK) {

                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }


            entity.status = responseEntity.getBody().returncode;
            withdrawOrderCacheRepository.save(entity);

            if(responseEntity.getBody().returncode != 1) {

                // TransactionHistory
                TransactionHistoryId transactionHistoryId = new TransactionHistoryId(request.userid, Long.valueOf(request.transactionid));

                Optional<TransactionHistory> transactionHistoryOptional = transactionHistoryRepository.findById(transactionHistoryId);
                if(!transactionHistoryOptional.isPresent()) {
                    response.returncode = responseEntity.getBody().returncode;
                    return response;
                }

                TransactionHistory transactionHistory = transactionHistoryOptional.get();
                transactionHistory.orderId = Long.valueOf(request.orderid);
                transactionHistory.status = -1; // that bai
                JsonObject txnDetailJsonObj = Json.createObjectBuilder().add("bankName", bankConfig.getBankName()).build();
                transactionHistory.txndetail = txnDetailJsonObj.toString();

                transactionHistoryRepository.save(transactionHistory);

                response.returncode = responseEntity.getBody().returncode;
                return response;
            }

            // record db
            WithdrawOrder withdrawOrder = new WithdrawOrder();
            withdrawOrder.orderId = Long.valueOf(request.orderid);
            withdrawOrder.f6cardno = entity.f6cardno;
            withdrawOrder.l4cardno = entity.l4cardno;
            withdrawOrder.amount = entity.amount;
            withdrawOrder.bankcode = entity.bankcode;
            withdrawOrderRepository.save(withdrawOrder);


            // TransactionHistory
            TransactionHistoryId transactionHistoryId = new TransactionHistoryId(request.userid, Long.valueOf(request.transactionid));

            Optional<TransactionHistory> transactionHistoryOptional = transactionHistoryRepository.findById(transactionHistoryId);
            if(!transactionHistoryOptional.isPresent()) {
                response.returncode = responseEntity.getBody().returncode;
                return response;
            }

            TransactionHistory transactionHistory = transactionHistoryOptional.get();
            transactionHistory.orderId = Long.valueOf(request.orderid);
            transactionHistory.status = 1; // thanh cong
            JsonObject txnDetailJsonObj = Json.createObjectBuilder()
                    .add("bankName", bankConfig.getBankName())
                    .add("cardNo", "***" + entity.l4cardno).build();
            transactionHistory.txndetail = txnDetailJsonObj.toString();

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
