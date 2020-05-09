package com.wallet.transactionhistory.list;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.TransactionHistory;
import com.wallet.database.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@RestController
public class TransactionHistoryListController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/txn-history/list")
    public TransactionHistoryListResponse history(@RequestBody TransactionHistoryListRequest request) {
        TransactionHistoryListResponse response = new TransactionHistoryListResponse();
        try {
            Query query = entityManager.createQuery("SELECT p FROM TransactionHistory p WHERE p.id.userId =:userid and p.timemilliseconds <= :starttime ORDER BY p.timemilliseconds DESC",
                    TransactionHistory.class);
            query.setParameter("userid", request.userid);
            query.setParameter("starttime", request.starttime);
            query.setMaxResults(request.pagesize);
            List<TransactionHistory> resultList = query.getResultList();

            if(resultList.isEmpty()) {
                response.returncode = ErrorCode.SUCCESS.getValue();
                return response;
            }

            for(TransactionHistory item : resultList) {
                TransactionHistoryEntity historyEnt = new TransactionHistoryEntity();
                historyEnt.transactionid = item.id.transactionId;
                historyEnt.orderid = item.orderId;
                historyEnt.amount = item.amount;
                historyEnt.servicetype = item.serviceType;
                historyEnt.sourceoffund = item.sourceofFund;
                historyEnt.timemilliseconds = item.timemilliseconds;
                historyEnt.txndetail = item.txndetail;
                response.histories.add(historyEnt);
            }
            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
