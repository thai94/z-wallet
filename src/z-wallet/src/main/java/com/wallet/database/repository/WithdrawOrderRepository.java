package com.wallet.database.repository;

import com.wallet.database.entity.WithdrawOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawOrderRepository extends JpaRepository<WithdrawOrder, Long> {
}
