package com.bank.sacombank.link;

import com.bank.sacombank.constant.ErrorCode;
import com.bank.sacombank.database.entity.BankCard;
import com.bank.sacombank.database.entity.BankCardId;
import com.bank.sacombank.database.entity.Customer;
import com.bank.sacombank.database.entity.WalletMapping;
import com.bank.sacombank.database.repository.BankCardRespository;
import com.bank.sacombank.database.repository.CustomerRespository;
import com.bank.sacombank.database.repository.WalletMappingRespository;
import com.bank.sacombank.entity.BaseResponse;
import com.bank.sacombank.utls.TokenGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
public class LinkController {

    @Autowired
    CustomerRespository customerRespository;

    @Autowired
    BankCardRespository bankCardRespository;

    @Autowired
    WalletMappingRespository walletMappingRespository;

    @PostMapping("/sacombank/link")
    public BaseResponse link(@RequestBody LinkRequest request) {
        LinkResponse response = new LinkResponse();
        try {
            if(StringUtils.isEmpty(request.cardnumber)) {
                response.returncode = ErrorCode.VALIDATE_CARD_NUMBER_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.cmnd)) {
                response.returncode = ErrorCode.VALIDATE_CMND_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.fullname)) {
                response.returncode = ErrorCode.VALIDATE_FULLNAME_INVALID.getValue();
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

            // check already linked?
            Optional<WalletMapping> walletMappingOptional = walletMappingRespository.findById(request.cardnumber);
            if(walletMappingOptional.isPresent()) {
                WalletMapping walletMapping = walletMappingOptional.get();
                if(walletMapping.status == 1) {
                    response.returncode = ErrorCode.BUZ_CARD_NUMBER_ALREADY_LINKED.getValue();
                    return response;
                }
            }

            if(walletMappingOptional.isPresent()) {
                WalletMapping walletMapping = walletMappingOptional.get();
                walletMapping.status = 1;
                walletMapping.bankToken = TokenGenerator.generateToken(request.phone);
                walletMapping.mappingDate = new Timestamp(System.currentTimeMillis());
                walletMappingRespository.save(walletMapping);
                response.banktoken = walletMapping.bankToken;
                response.returncode = ErrorCode.SUCCESS.getValue();
                return response;
            }

            WalletMapping walletMapping = new WalletMapping();
            walletMapping.cardNumber = request.cardnumber;
            walletMapping.status = 1;
            walletMapping.bankToken = TokenGenerator.generateToken(request.phone);
            walletMapping.mappingDate = new Timestamp(System.currentTimeMillis());
            walletMappingRespository.save(walletMapping);
            response.banktoken = walletMapping.bankToken;
            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
