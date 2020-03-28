package com.wallet.database.repository;

import com.wallet.database.entity.MoneyTransferOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyTransferOrderRepository extends JpaRepository<MoneyTransferOrder, Long> {
}
