package com.bank.sacombank.database.repository;

import com.bank.sacombank.database.entity.Balance;
import com.bank.sacombank.database.entity.BalanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRespository extends JpaRepository<Balance, BalanceId> {
    public Balance findTopByIdCardNumberOrderByUpdateDateDesc(String cardNumber);
}
