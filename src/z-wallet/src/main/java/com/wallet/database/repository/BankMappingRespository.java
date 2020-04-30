package com.wallet.database.repository;

import com.wallet.database.entity.BankMapping;
import com.wallet.database.entity.BankMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankMappingRespository extends JpaRepository<BankMapping, BankMappingId> {
    public BankMapping findOneByIdUseridAndF6cardnoAndL4cardno(String userid, String f6cardno, String l4cardno);
    public List<BankMapping> findAllByIdUseridAndStatus(String userId, int status);
}
