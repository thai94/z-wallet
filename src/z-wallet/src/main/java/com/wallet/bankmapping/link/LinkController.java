package com.wallet.bankmapping.link;

import com.wallet.configuration.SecureHttpConverter;
import com.wallet.constant.ErrorCode;
import com.wallet.constant.Service;
import com.wallet.constant.SupportBank;
import com.wallet.database.entity.BankMapping;
import com.wallet.database.entity.BankMappingId;
import com.wallet.database.entity.UserNotify;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.notify.send.SendNotifyService;
import com.wallet.properties.BankConfig;
import com.wallet.properties.BankProperties;
import com.wallet.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LinkController {

    @Autowired
    BankProperties bankProperties;

    @Autowired
    BankMappingRespository bankMappingRespository;

    @Autowired
    SendNotifyService sendNotifyService;

    @PostMapping("/bank-mapping/link")
    public BaseResponse link(@RequestBody LinkRequest request) {
        LinkResponse response = new LinkResponse();
        try {

            // validate
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.bankcode)) {
                response.returncode = ErrorCode.VALIDATE_BANK_CODE_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.cardno)) {
                response.returncode = ErrorCode.VALIDATE_CAR_NUMBER_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.fullname)) {
                response.returncode = ErrorCode.VALIDATE_FULL_NAME_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.cmnd)) {
                response.returncode = ErrorCode.VALIDATE_CMND_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.phone)) {
                response.returncode = ErrorCode.VALIDATE_PHONE_INVALID.getValue();
                return response;
            }

            //  call bank
            BankConfig bankConfig = bankProperties.getConnector().get(request.bankcode);
            if(bankConfig == null) {
                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            BankLinkRequest bankLinkRequest = new BankLinkRequest();
            bankLinkRequest.cardnumber = request.cardno;
            bankLinkRequest.cmnd = request.cmnd;
            bankLinkRequest.fullname = request.fullname;
            bankLinkRequest.phone = request.phone;

            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> converterList = new ArrayList<>();
            converterList.add(new SecureHttpConverter(bankConfig.secretKey, bankConfig.salt));
            restTemplate.setMessageConverters(converterList);

            ResponseEntity<BankLinkResponse> bankResponse = restTemplate.postForEntity(bankConfig.baseUrl + bankConfig.linkMethod, bankLinkRequest, BankLinkResponse.class);
            // check response
            if(bankResponse.getStatusCode() != HttpStatus.OK) {
                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            // fail
            BankLinkResponse bankLinkResponse = bankResponse.getBody();
            if(bankLinkResponse.returncode != ErrorCode.SUCCESS.getValue()) {
                response.returncode = ErrorCode.SUCCESS.getValue();
                response.bankreturncode = bankLinkResponse.returncode;
                return response;
            }

            // success
            Optional<BankMapping> bankMappingOpt = bankMappingRespository.findById(new BankMappingId(request.userid, request.cardno));
            if(!bankMappingOpt.isPresent()) {
                BankMapping bankMapping = new BankMapping();
                bankMapping.id.userid = request.userid;
                bankMapping.id.cardNumber = request.cardno;
                bankMapping.fulllname = request.fullname;
                bankMapping.f6cardno = request.cardno.substring(0,6);
                bankMapping.l4cardno =  request.cardno.substring(request.cardno.length() - 4);
                bankMapping.bankToken = bankLinkResponse.banktoken;
                bankMapping.cardName = bankConfig.bankName;
                bankMapping.bankCode = request.bankcode;
                bankMapping.status = 1;

                bankMappingRespository.save(bankMapping);

                response.returncode = ErrorCode.SUCCESS.getValue();
                response.bankreturncode = bankLinkResponse.returncode;


                // notify
                UserNotify notify = new UserNotify();
                notify.notifyId = System.currentTimeMillis();
                notify.userId = request.userid;
                notify.serviceType = Service.LINK_CARD.getKey();
                notify.title = String.format("Liên kết ngân hàng %s", SupportBank.fromValue(request.bankcode).getBankName());

                LinkBankNotifyTemplate linkBankNotifyTemplate = new LinkBankNotifyTemplate();
                linkBankNotifyTemplate.bankCode = request.bankcode;
                linkBankNotifyTemplate.first6CardNo = request.cardno.substring(0,6);
                linkBankNotifyTemplate.last6CardNo = request.cardno.substring(request.cardno.length() - 4);

                notify.content = GsonUtils.toJsonString(linkBankNotifyTemplate);
                notify.createDate = System.currentTimeMillis();
                sendNotifyService.send(notify);

                return response;
            }

            BankMapping bankMapping = bankMappingOpt.get();
            bankMapping.status = 1;
            bankMapping.bankToken = bankLinkResponse.banktoken;
            bankMappingRespository.save(bankMapping);
            response.returncode = ErrorCode.SUCCESS.getValue();
            response.bankreturncode = bankLinkResponse.returncode;

            // notify
            UserNotify notify = new UserNotify();
            notify.notifyId = System.currentTimeMillis();
            notify.userId = request.userid;
            notify.serviceType = Service.LINK_CARD.getKey();
            notify.title = String.format("Liên kết ngân hàng %s", SupportBank.fromValue(request.bankcode).getBankName());

            LinkBankNotifyTemplate linkBankNotifyTemplate = new LinkBankNotifyTemplate();
            linkBankNotifyTemplate.bankCode = request.bankcode;
            linkBankNotifyTemplate.first6CardNo = request.cardno.substring(0,6);
            linkBankNotifyTemplate.last6CardNo = request.cardno.substring(request.cardno.length() - 4);

            notify.content = GsonUtils.toJsonString(linkBankNotifyTemplate);
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
