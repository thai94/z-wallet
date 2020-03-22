package com.bank.msb.unlink;

import com.bank.msb.constant.ErrorCode;
import com.bank.msb.database.entity.BankCard;
import com.bank.msb.database.entity.BankCardId;
import com.bank.msb.database.entity.Customer;
import com.bank.msb.database.entity.WalletMapping;
import com.bank.msb.database.repository.BankCardRespository;
import com.bank.msb.database.repository.CustomerRespository;
import com.bank.msb.database.repository.WalletMappingRespository;
import com.bank.msb.entity.BaseResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UnLinkController {

    @Autowired
    CustomerRespository customerRespository;

    @Autowired
    BankCardRespository bankCardRespository;

    @Autowired
    WalletMappingRespository walletMappingRespository;

    @PostMapping("/msb/un-link")
    public BaseResponse unlink(@RequestBody UnLinkRequest request) {

        UnLinkResponse response = new UnLinkResponse();
        try {
            if(StringUtils.isEmpty(request.cardnumber)) {
                response.returncode = ErrorCode.VALIDATE_CARD_NUMBER_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.cmnd)) {
                response.returncode = ErrorCode.VALIDATE_CMND_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.banktoken)) {
                response.returncode = ErrorCode.VALIDATE_BANK_TOKEN_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.phone)) {
                response.returncode = ErrorCode.VALIDATE_PHONE_INVALID.getValue();
                return response;
            }

            // check customer exist
            Optional<Customer> customerOpt = customerRespository.findById(request.cmnd);
            if(!customerOpt.isPresent()) {
                response.returncode = ErrorCode.BUZ_CUSTOMER_NOT_FOUND.getValue();
                return response;
            }

            // check cardnumber exist
            Optional<BankCard> bankCardOptional = bankCardRespository.findById(new BankCardId(request.cmnd, request.cardnumber));
            if(!bankCardOptional.isPresent()) {
                response.returncode = ErrorCode.BUZ_CARD_NUMBER_NOT_FOUND.getValue();
                return response;
            }

            // validate bank token
            Optional<WalletMapping> walletMappingOpt = walletMappingRespository.findById(request.cardnumber);
            if(!walletMappingOpt.isPresent()) {
                response.returncode = ErrorCode.BUZ_CARD_NUMBER_NOT_MAPPING.getValue();
                return response;
            }

            if(!StringUtils.equals(walletMappingOpt.get().bankToken, request.banktoken)) {
                response.returncode = ErrorCode.BUZ_BANK_TOKEN_WRONG.getValue();
                return response;
            }

            if(walletMappingOpt.isPresent()) {
                WalletMapping walletMapping = walletMappingOpt.get();
                if(walletMapping.status == 2) {
                    response.returncode = ErrorCode.BUZ_CARD_NUMBER_ALREADY_UN_LINKED.getValue();
                    return response;
                }
            }

            WalletMapping walletMapping = walletMappingOpt.get();
            walletMapping.status = 2;
            walletMappingRespository.save(walletMapping);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }

}
