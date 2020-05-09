package com.wallet.pay.submittran;

import com.wallet.configuration.SecureHttpConverter;
import com.wallet.constant.ErrorCode;
import com.wallet.constant.Service;
import com.wallet.database.entity.BankMapping;
import com.wallet.database.entity.Transaction;
import com.wallet.database.entity.TransactionHistory;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.database.repository.TransactionHistoryRepository;
import com.wallet.database.repository.TransactionRespository;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.properties.*;
import com.wallet.utils.GenId;
import com.wallet.wallet.subtractcash.SubtractCashRequest;
import com.wallet.wallet.subtractcash.SubtractCashResponse;
import org.apache.commons.lang3.StringUtils;
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
public class SubmitTransController {

    @Autowired
    WalletUserRespository walletUserRespository;

    @Autowired
    WalletProperties walletConfig;

    @Autowired
    BankProperties bankProperties;

    @Autowired
    BankMappingRespository bankMappingRespository;

    @Autowired
    TransactionRespository transactionRespository;

    @Autowired
    CallbackProperties callbackProperties;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @PostMapping("/charge-order/submit-trans")
    public BaseResponse submitTrans(@RequestBody SubmitTransRequest request) {
        SubmitTransResponse response = new SubmitTransResponse();
        try {
            // validate
            if(!validateRequest(request, response)) {
                return response;
            }

            String transactionId = GenId.genId();

            // check duplicate
            Transaction transaction = transactionRespository.findByOrderIdAndServiceType(request.orderid, request.servicetype);
            if(transaction != null) {
                response.returncode = ErrorCode.DUPLICATE_TRANSACTION.getValue();
                return response;
            }

            transaction = new Transaction();
            transaction.transactionId = Long.valueOf(transactionId);
            transaction.orderId = request.orderid;
            transaction.userId = request.userid;
            transaction.sourceOfFund = request.sourceoffund;
            transaction.chargeTime = System.currentTimeMillis();
            transaction.amount = request.amount;
            transaction.serviceType = request.servicetype;


            Service service = Service.find(request.servicetype);
            // pay for wallet
            if(request.sourceoffund == 1) {
                RestTemplate restTemplate = new RestTemplate();
                SubtractCashRequest subtractCashRequest = new SubtractCashRequest();
                subtractCashRequest.userid = request.userid;
                subtractCashRequest.amount = request.amount;
                subtractCashRequest.transactionid = Long.valueOf(transactionId);

                ResponseEntity<SubtractCashResponse> responseEntity = restTemplate.postForEntity(walletConfig.baseUrl + walletConfig.subTractCashMethod, subtractCashRequest, SubtractCashResponse.class);
                if(responseEntity.getStatusCode() != HttpStatus.OK) {

                    transaction.status = 0;
                    transactionRespository.save(transaction);

                    response.returncode = ErrorCode.EXCEPTION.getValue();
                    return response;
                }

                transaction.status = responseEntity.getBody().getReturncode() == 1 ? 1: 0;
                transactionRespository.save(transaction);

                if(responseEntity.getBody().getReturncode() != 1) {
                    response.returncode = responseEntity.getBody().getReturncode();
                    return response;
                }

                // insert TransactionHistory
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.id.userId = request.userid;
                transactionHistory.id.transactionId = Long.valueOf(transactionId);
                transactionHistory.orderId = request.orderid;
                transactionHistory.amount = request.amount;
                transactionHistory.serviceType = request.servicetype;
                transactionHistory.sourceofFund = request.sourceoffund;
                transactionHistory.timemilliseconds = System.currentTimeMillis();
                transactionHistory.txndetail = "";

                transactionHistoryRepository.saveAndFlush(transactionHistory);

                // do callback

                CallbackConfig callbackConfig = callbackProperties.service.get(service.getValue());
                CallbackRequest callbackRequest = new CallbackRequest();
                callbackRequest.userid = request.userid;
                callbackRequest.transactionid = Long.valueOf(transactionId);
                callbackRequest.orderid = String.valueOf(request.orderid);

                ResponseEntity<CallbackResponse> callbackResponseResponseEntity = restTemplate.postForEntity(callbackConfig.baseUrl + callbackConfig.callbackMethod, callbackRequest, CallbackResponse.class);
                if(callbackResponseResponseEntity.getStatusCode() != HttpStatus.OK) {

                    response.returncode = ErrorCode.CALL_BACK_FAIL.getValue();
                    return response;
                }

                if(callbackResponseResponseEntity.getBody().returncode != 1) {
                    response.returncode = callbackResponseResponseEntity.getBody().returncode;
                    return response;
                }

                response.returncode = ErrorCode.SUCCESS.getValue();
                return response;
            } else {
                // pay for bank
                BankConfig bankConfig = bankProperties.getConnector().get(request.bankcode);
                if(bankConfig == null) {
                    transaction.status = 0;
                    transactionRespository.save(transaction);

                    response.returncode = ErrorCode.EXCEPTION.getValue();
                    return response;
                }

                BankMapping bankMapping = bankMappingRespository.findOneByIdUseridAndF6cardnoAndL4cardno(request.userid, request.f6cardno, request.l4cardno);
                if(bankMapping == null) {
                    transaction.status = 0;
                    transactionRespository.save(transaction);
                    response.returncode = ErrorCode.USER_HAS_NOT_MAPPING_YET.getValue();
                    return response;
                }

                BankPayRequest bankPayRequest = new BankPayRequest();
                bankPayRequest.banktoken = bankMapping.bankToken;
                bankPayRequest.cardnumber = bankMapping.id.cardNumber;
                bankPayRequest.transactionid = transactionId;
                bankPayRequest.amount = request.amount;

                RestTemplate restTemplate = new RestTemplate();
                List<HttpMessageConverter<?>> converterList = new ArrayList<>();
                converterList.add(new SecureHttpConverter(bankConfig.secretKey, bankConfig.salt));
                restTemplate.setMessageConverters(converterList);
                ResponseEntity<BankPayResponse> responseEntity = restTemplate.postForEntity(bankConfig.baseUrl + bankConfig.payMethod, bankPayRequest, BankPayResponse.class);

                if(responseEntity.getStatusCode() != HttpStatus.OK) {

                    transaction.status = 0;
                    transactionRespository.save(transaction);

                    response.returncode = ErrorCode.EXCEPTION.getValue();
                    return response;
                }

                transaction.status = responseEntity.getBody().getReturncode() == 1? 1: 0;
                transactionRespository.save(transaction);

                if(responseEntity.getBody().getReturncode() != 1) {
                    response.returncode = responseEntity.getBody().getReturncode();
                    return response;
                }

                // insert TransactionHistory
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.id.userId = request.userid;
                transactionHistory.id.transactionId = Long.valueOf(transactionId);
                transactionHistory.orderId = 0;
                transactionHistory.amount = request.amount;
                transactionHistory.serviceType = request.servicetype;
                transactionHistory.sourceofFund = request.sourceoffund;
                transactionHistory.timemilliseconds = System.currentTimeMillis();
                JsonObject txnDetailJsonObj = Json.createObjectBuilder()
                        .add("bankName", bankConfig.getBankName())
                        .add("cardNo", "***" + request.l4cardno).build();
                transactionHistory.txndetail = txnDetailJsonObj.toString();

                transactionHistoryRepository.save(transactionHistory);

                // do callback

                CallbackConfig callbackConfig = callbackProperties.service.get(service.getValue());
                CallbackRequest callbackRequest = new CallbackRequest();
                callbackRequest.userid = request.userid;
                callbackRequest.transactionid = Long.valueOf(transactionId);
                callbackRequest.orderid = String.valueOf(request.orderid);

                RestTemplate callBackRestTemplate = new RestTemplate();
                ResponseEntity<CallbackResponse> callbackResponseResponseEntity = callBackRestTemplate.postForEntity(callbackConfig.baseUrl + callbackConfig.callbackMethod, callbackRequest, CallbackResponse.class);
                if(callbackResponseResponseEntity.getStatusCode() != HttpStatus.OK) {

                    response.returncode = ErrorCode.CALL_BACK_FAIL.getValue();
                    return response;
                }

                if(callbackResponseResponseEntity.getBody().returncode != 1) {
                    response.returncode = callbackResponseResponseEntity.getBody().returncode;
                    return response;
                }

                response.returncode = ErrorCode.SUCCESS.getValue();
                return response;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }

    }

    private boolean validateRequest(SubmitTransRequest request, SubmitTransResponse response) {
        if(StringUtils.isEmpty(request.userid)) {
            response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
            return false;
        }

        if(request.orderid <= 0) {
            response.returncode = ErrorCode.VALIDATE_ORDER_ID_INVALID.getValue();
            return false;
        }

        if(request.sourceoffund != 1 && request.sourceoffund != 2) {
            response.returncode = ErrorCode.VALIDATE_SOURCE_OF_FUN_INVALID.getValue();
            return false;
        }

        // ATM
        if(request.sourceoffund == 2) {
            if(StringUtils.isEmpty(request.bankcode)) {
                response.returncode = ErrorCode.VALIDATE_BANK_CODE_INVALID.getValue();
                return false;
            }

            if(StringUtils.isEmpty(request.f6cardno)) {
                response.returncode = ErrorCode.VALIDATE_F6CARD_NO_INVALID.getValue();
                return false;
            }

            if(StringUtils.isEmpty(request.l4cardno)) {
                response.returncode = ErrorCode.VALIDATE_L4CARD_NO_INVALID.getValue();
                return false;
            }
        }

        if(request.amount <= 0) {
            response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
            return false;
        }

        if(request.servicetype <= 0) {
            response.returncode = ErrorCode.VALIDATE_SERVICE_TYPE_INVALID.getValue();
            return false;
        }

        if(StringUtils.isEmpty(request.pin)) {
            response.returncode = ErrorCode.VALIDATE_PIN_INVALID.getValue();
            return false;
        }

        Service service = Service.find(request.servicetype);
        if(service == null) {
            response.returncode = ErrorCode.CHECK_SERVICE_DOES_NOT_EXIST.getValue();
            return false;
        }

        // varify pin
        Optional<WalletUser> walletUserOptional = walletUserRespository.findById(request.userid);
        if(!walletUserOptional.isPresent()) {
            response.returncode = ErrorCode.USER_PIN_WRONG.getValue();
            return false;
        }

        WalletUser walletUser = walletUserOptional.get();
        if(!walletUser.pin.equals(request.pin)) {
            response.returncode = ErrorCode.USER_PIN_WRONG.getValue();
            return false;
        }
        return true;
    }
}
