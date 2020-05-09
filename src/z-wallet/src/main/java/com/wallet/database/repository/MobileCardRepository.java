package com.wallet.database.repository;

import com.wallet.database.entity.MobileCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileCardRepository extends JpaRepository<MobileCard, String> {
    public MobileCard findTopOneByStatusAndAmount(int status, long amount);
}
