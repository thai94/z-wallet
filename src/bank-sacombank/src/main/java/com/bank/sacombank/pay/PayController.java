package com.bank.sacombank.pay;

import com.bank.sacombank.constant.ErrorCode;
import com.bank.sacombank.database.entity.Balance;
import com.bank.sacombank.database.entity.BalanceId;
import com.bank.sacombank.database.entity.WalletMapping;
import com.bank.sacombank.database.repository.BalanceRespository;
import com.bank.sacombank.database.repository.WalletMappingRespository;
import com.bank.sacombank.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
public class PayController {

    @Autowired
    WalletMappingRespository walletMappingRespository;

    @Autowired
    BalanceRespository balanceRespository;

    @PostMapping("/sacombank/pay")
    public BaseResponse pay(@RequestBody PayRequest request) {
        PayResponse response = new PayResponse();
        try {
            if(StringUtils.isEmpty(request.banktoken)) {
                response.returncode = ErrorCode.VALIDATE_BANK_TOKEN_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.cardnumber)) {
                response.returncode = ErrorCode.VALIDATE_CARD_NUMBER_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.transactionid)) {
                response.returncode = ErrorCode.VALIDATE_TRANSACTION_ID_INVALID.getValue();
                return response;
            }

            if(request.amount <= 0) {
                response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
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

            // check dup
            Optional<Balance> balanceOpt = balanceRespository.findById(new BalanceId(request.cardnumber, request.transactionid));
            if(balanceOpt.isPresent()) {
                response.returncode = ErrorCode.BUZ_TRANSACTION_DUPLICATE.getValue();
                return response;
            }

            // - cash
            Balance balance = balanceRespository.findTopByIdCardNumberOrderByUpdateDateDesc(request.cardnumber);
            if(balance == null) {
                response.returncode = ErrorCode.BUZ_BALANCE_NOT_ENOUGHT.getValue();
                return response;
            }

            long amount = balance.balance - request.amount;
            if(amount < 0) {
                response.returncode = ErrorCode.BUZ_BALANCE_NOT_ENOUGHT.getValue();
                return response;
            }

            Balance newBalance = new Balance();
            newBalance.id.cardNumber = request.cardnumber;
            newBalance.id.transactionId = request.transactionid;
            newBalance.balance = amount;
            newBalance.updateDate = new Timestamp(System.currentTimeMillis());

            balanceRespository.save(newBalance);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
