package com.wallet.database.repository;

import com.wallet.database.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRespository extends JpaRepository<Transaction, Long> {
    public Transaction findByOrderIdAndServiceType(long orderId, int serviceType);


}
