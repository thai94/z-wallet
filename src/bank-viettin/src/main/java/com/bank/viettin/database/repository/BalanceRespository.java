package com.bank.viettin.database.repository;

import com.bank.viettin.database.entity.Balance;
import com.bank.viettin.database.entity.BalanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRespository extends JpaRepository<Balance, BalanceId> {
    public Balance findTopByIdCardNumberOrderByUpdateDateDesc(String cardNumber);
}
