package com.wallet.wallet.addcash;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.Wallet;
import com.wallet.database.entity.WalletId;
import com.wallet.database.repository.WalletRepository;
import com.wallet.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;

@RestController
@Transactional
public class AddCashController {

    @Autowired
    WalletRepository walletRepository;

    @PostMapping("/wallet/add-cash")
    public BaseResponse addCash(@RequestBody AddCashRequest addCashRequest) {
        AddCashResponse response = new AddCashResponse();
        try {
            if(StringUtils.isEmpty(addCashRequest.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(addCashRequest.amount <= 0) {
                response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
                return response;
            }

            if(addCashRequest.transactionid <= 0) {
                response.returncode = ErrorCode.VALIDATE_TRANSACTION_ID_INVALID.getValue();
                return response;
            }

            Optional<Wallet> walletOpt = walletRepository.findById(new WalletId(addCashRequest.userid, addCashRequest.transactionid));
            if(walletOpt.isPresent()) {
                response.returncode = ErrorCode.VALIDATE_TRANSACTION_DUPLICATE.getValue();
                return response;
            }


            Wallet wallet = walletRepository.findTopByIdUserIdOrderByUpdateDateDesc(addCashRequest.userid);
            Wallet newWallet = new Wallet();
            if(wallet == null) {
                newWallet = new Wallet();
                newWallet.id.userId = addCashRequest.userid;
                newWallet.id.transactionId = addCashRequest.transactionid;
                newWallet.balance = addCashRequest.amount;
                newWallet.updateDate = new Timestamp(System.currentTimeMillis());
            } else {
                newWallet.id.userId = addCashRequest.userid;
                newWallet.id.transactionId = addCashRequest.transactionid;
                newWallet.balance = wallet.balance + addCashRequest.amount;
                newWallet.updateDate = new Timestamp(System.currentTimeMillis());
            }
            walletRepository.save(newWallet);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
