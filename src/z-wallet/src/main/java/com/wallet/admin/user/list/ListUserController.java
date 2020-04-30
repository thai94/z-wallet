package com.wallet.admin.user.list;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ListUserController {

    @Autowired
    WalletUserRespository walletUserRespository;

    @PostMapping("/admin/user/list")
    public ListUserResponse search(@RequestBody ListUserRequest request) {
        ListUserResponse response = new ListUserResponse();
        try {
            List<WalletUser> userList = findByCriteria(request);
            response.users = userList;
            response.returncode = ErrorCode.SUCCESS.getValue();
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }

        return response;
    }

    List<WalletUser> findByCriteria(ListUserRequest search) {
        return walletUserRespository.findAll(new Specification<WalletUser>() {
            @Override
            public Predicate toPredicate(Root<WalletUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(StringUtils.isNotEmpty(search.userid)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("userId"), search.userid)));
                }

                if(StringUtils.isNotEmpty(search.fullname)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("fullName"), "%" + search.fullname + "%")));
                }

                if(StringUtils.isNotEmpty(search.phone)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("phone"), search.phone)));
                }

                if(StringUtils.isNotEmpty(search.cmnd)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cmnd"), search.cmnd)));
                }

                if(StringUtils.isNotEmpty(search.address)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("address"), "%" + search.address + "%")));
                }

                if(StringUtils.isNotEmpty(search.verify)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("verify"), search.verify)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
