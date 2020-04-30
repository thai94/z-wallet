package com.wallet.bankmapping.unlink;

import com.wallet.constant.ErrorCode;
import com.wallet.constant.Service;
import com.wallet.constant.SupportBank;
import com.wallet.database.entity.BankMapping;
import com.wallet.database.entity.UserNotify;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.notify.send.SendNotifyService;
import com.wallet.properties.BankConfig;
import com.wallet.properties.BankProperties;
import com.wallet.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class UnlinkController {

    @Autowired
    BankProperties bankProperties;

    @Autowired
    BankMappingRespository bankMappingRespository;

    @Autowired
    WalletUserRespository walletUserRespository;

    @Autowired
    SendNotifyService sendNotifyService;

    @PostMapping("/bank-mapping/unlink")
    public BaseResponse unlink(@RequestBody UnlinkRequest request) {
        UnlinkResponse response = new UnlinkResponse();

        try {
            // validate
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

            // call bank
            BankConfig bankConfig = bankProperties.getConnector().get(request.bankcode);
            if(bankConfig == null) {
                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            BankMapping bankMapping = bankMappingRespository.findOneByIdUseridAndF6cardnoAndL4cardno(request.userid, request.f6cardno, request.l4cardno);
            if(bankMapping == null) {
                response.returncode = ErrorCode.USER_HAS_NOT_MAPPING_YET.getValue();
                return response;
            }

            Optional<WalletUser> walletUserOpt = walletUserRespository.findById(request.userid);
            if(!walletUserOpt.isPresent()) {
                response.returncode = ErrorCode.USER_HAS_NOT_MAPPING_YET.getValue();
                return response;
            }

            BankUnlinkRequest bankUnlinkRequest = new BankUnlinkRequest();
            bankUnlinkRequest.cardnumber = bankMapping.id.cardNumber;
            bankUnlinkRequest.banktoken = bankMapping.bankToken;
            bankUnlinkRequest.cmnd = walletUserOpt.get().cmnd;
            bankUnlinkRequest.phone = walletUserOpt.get().phone;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<BankUnlinkResponse> bankResponse = restTemplate.postForEntity(bankConfig.baseUrl + bankConfig.unLinkMethod, bankUnlinkRequest, BankUnlinkResponse.class);
            // check response
            if(bankResponse.getStatusCode() != HttpStatus.OK) {
                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            // fail
            BankUnlinkResponse bankUnlinkResponse = bankResponse.getBody();
            if(bankUnlinkResponse.returncode != ErrorCode.SUCCESS.getValue()) {
                response.returncode = ErrorCode.SUCCESS.getValue();
                response.bankreturncode = bankUnlinkResponse.returncode;
                return response;
            }

            // success
            bankMapping.status = 2;
            bankMappingRespository.save(bankMapping);

            response.returncode = ErrorCode.SUCCESS.getValue();
            response.bankreturncode = bankUnlinkResponse.returncode;

            // notify
            UserNotify notify = new UserNotify();
            notify.notifyId = System.currentTimeMillis();
            notify.userId = request.userid;
            notify.serviceType = Service.UN_LINK_CARD.getKey();
            notify.title = String.format("Hủy liên kết ngân hàng %s", SupportBank.fromValue(request.bankcode).getBankName());

            UnlinkBankNotifyTemplate unlinkBankNotifyTemplate = new UnlinkBankNotifyTemplate();
            unlinkBankNotifyTemplate.bankCode = request.bankcode;
            unlinkBankNotifyTemplate.first6CardNo = request.f6cardno;
            unlinkBankNotifyTemplate.last6CardNo = request.l4cardno;

            notify.content = GsonUtils.toJsonString(unlinkBankNotifyTemplate);
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
