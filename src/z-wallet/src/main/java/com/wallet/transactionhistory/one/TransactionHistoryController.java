package com.wallet.transactionhistory.one;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.TransactionHistory;
import com.wallet.database.entity.TransactionHistoryId;
import com.wallet.database.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionHistoryController {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @PostMapping("/txn-history/detail")
    public TransactionHistoryResponse transactionHistory(@RequestBody TransactionHistoryRequest request) {
        TransactionHistoryResponse response = new TransactionHistoryResponse();
        try {
            Optional<TransactionHistory> transactionHistoryOptional = transactionHistoryRepository.findById(new TransactionHistoryId(request.userid, request.transactionid));
            if(!transactionHistoryOptional.isPresent()) {
                response.returncode = ErrorCode.EXCEPTION.getValue();
                return response;
            }

            TransactionHistory transactionHistory = transactionHistoryOptional.get();

            response.data.transactionid = transactionHistory.id.transactionId;
            response.data.orderid = transactionHistory.orderId;
            response.data.amount = transactionHistory.amount;
            response.data.servicetype = transactionHistory.serviceType;
            response.data.sourceoffund = transactionHistory.sourceofFund;
            response.data.timemilliseconds = transactionHistory.timemilliseconds;
            response.data.txndetail = transactionHistory.txndetail;

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
