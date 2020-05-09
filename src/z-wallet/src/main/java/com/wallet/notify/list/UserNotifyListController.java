package com.wallet.notify.list;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.UserNotify;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@RestController
public class UserNotifyListController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/notify/transactionhistory")
    public UserNotifyListResponse history(@RequestBody UserNotifyListRequest request) {
        UserNotifyListResponse response = new UserNotifyListResponse();

        try {
            Query query = entityManager.createQuery("SELECT p FROM UserNotify p WHERE p.userId =:userid and p.createDate  <= :starttime ORDER BY p.createDate DESC",
                    UserNotify.class);
            query.setParameter("userid", request.userid);
            query.setParameter("starttime", request.starttime);
            query.setMaxResults(request.pagesize);
            List<UserNotify> resultList = query.getResultList();

            if(resultList.isEmpty()) {
                response.returncode = ErrorCode.SUCCESS.getValue();
                return response;
            }

            for (UserNotify notify : resultList) {
                UserNotifyEntity userNotifyEntity = new UserNotifyEntity(notify);
                response.histories.add(userNotifyEntity);
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
