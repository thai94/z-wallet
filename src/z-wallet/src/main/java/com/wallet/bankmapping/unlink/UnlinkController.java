package com.wallet.bankmapping.unlink;

import com.wallet.bankmapping.link.BankLinkRequest;
import com.wallet.bankmapping.link.BankLinkResponse;
import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.BankMapping;
import com.wallet.database.entity.BankMappingId;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.properties.BankConfig;
import com.wallet.properties.BankProperties;
import jdk.nashorn.internal.runtime.options.Option;
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

            BankUnlinkResponse bankUnlinkResponse = bankResponse.getBody();
            if(bankUnlinkResponse.returncode != ErrorCode.SUCCESS.getValue()) {
                response.returncode = ErrorCode.SUCCESS.getValue();
                response.bankreturncode = bankUnlinkResponse.returncode;
                return response;
            }

            bankMapping.status = 2;
            bankMappingRespository.save(bankMapping);

            response.returncode = ErrorCode.SUCCESS.getValue();
            response.bankreturncode = bankUnlinkResponse.returncode;
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
