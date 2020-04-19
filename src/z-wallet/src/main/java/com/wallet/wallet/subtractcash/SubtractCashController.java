package com.wallet.wallet.subtractcash;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.Wallet;
import com.wallet.database.entity.WalletId;
import com.wallet.database.repository.WalletRepository;
import com.wallet.entity.BaseResponse;
import com.wallet.utils.GsonUtils;
import com.wallet.wallet.topic.WalletMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
public class SubtractCashController {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    private SimpMessagingTemplate webSocket;

    @PostMapping("/wallet/subtract-cash")
    public BaseResponse subtractCash(@RequestBody SubtractCashRequest request) {
        SubtractCashResponse response = new SubtractCashResponse();
        try {
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(request.amount <= 0) {
                response.returncode = ErrorCode.VALIDATE_AMOUNT_INVALID.getValue();
                return response;
            }

            if(request.transactionid <= 0) {
                response.returncode = ErrorCode.VALIDATE_TRANSACTION_ID_INVALID.getValue();
                return response;
            }

            Optional<Wallet> walletOpt = walletRepository.findById(new WalletId(request.userid, request.transactionid));
            if(walletOpt.isPresent()) {
                response.returncode = ErrorCode.VALIDATE_TRANSACTION_DUPLICATE.getValue();
                return response;
            }


            Wallet wallet = walletRepository.findTopByIdUserIdOrderByUpdateDateDesc(request.userid);
            if(wallet == null) {
                response.returncode = ErrorCode.BALANCE_NOT_ENOUGHT.getValue();
                return response;
            }

            long amount = wallet.balance - request.amount;
            if(amount < 0) {
                response.returncode = ErrorCode.BALANCE_NOT_ENOUGHT.getValue();
                return response;
            }

            Wallet newWallet = new Wallet();
            newWallet.id.userId = request.userid;
            newWallet.id.transactionId = request.transactionid;
            newWallet.balance = amount;
            newWallet.updateDate = new Timestamp(System.currentTimeMillis());
            walletRepository.save(newWallet);

            // send websocket msg
            String socketMsg = GsonUtils.toJsonString(new WalletMessage(newWallet.id.userId, newWallet.balance));
            webSocket.convertAndSend("/ws/topic/updated_wallet", socketMsg);

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
