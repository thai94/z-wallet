package com.wallet.database.repository;

import com.wallet.database.entity.TransactionHistory;
import com.wallet.database.entity.TransactionHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, TransactionHistoryId> {
}
