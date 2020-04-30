package com.wallet.pay.history;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.Transaction;
import com.wallet.database.repository.TransactionRespository;
import com.wallet.entity.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class TransactionHistoryController {

    @Autowired
    TransactionRespository transactionRespository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/charge-order/history")
    public BaseResponse list(@RequestBody TransactionHistoryRequest request) {

        TransactionHistoryResponse response = new TransactionHistoryResponse();

        try {
             Query query = entityManager.createQuery("SELECT p FROM Transaction p WHERE p.userId =:userid and p.chargeTime <= :starttime ORDER BY p.chargeTime DESC",
                    Transaction.class);
            query.setParameter("userid", request.userid);
             query.setParameter("starttime", request.starttime);
             query.setMaxResults(request.pagesize);
             List<Transaction> resultList = query.getResultList();
             if(resultList.isEmpty()) {
                 response.returncode = ErrorCode.SUCCESS.getValue();
                 return response;
             }

             for(Transaction transaction: resultList) {
                 TransactionEntity transactionEntity = new TransactionEntity();
                 transactionEntity.transactionid = transaction.transactionId;
                 transactionEntity.orderid = transaction.orderId;
                 transactionEntity.sourceoffund = transaction.sourceOfFund;

                 Date date = new Date();
                 date.setTime(transaction.chargeTime);
                 String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
                 transactionEntity.chargetime = formattedDate;

                 transactionEntity.amount = transaction.amount;
                 transactionEntity.servicetype  = transaction.serviceType;
                 transactionEntity.timemilliseconds = transaction.chargeTime;

                 response.histories.add(transactionEntity);
             }

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;

        } catch(Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
