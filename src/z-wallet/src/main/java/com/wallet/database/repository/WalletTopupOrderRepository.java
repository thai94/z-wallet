package com.wallet.database.repository;

import com.wallet.database.entity.BankConfig;
import com.wallet.database.entity.WalletTopupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTopupOrderRepository extends JpaRepository<WalletTopupOrder, Long> {
}
