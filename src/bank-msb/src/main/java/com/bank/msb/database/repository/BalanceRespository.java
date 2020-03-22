package com.bank.msb.database.repository;

import com.bank.msb.database.entity.Balance;
import com.bank.msb.database.entity.BalanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRespository extends JpaRepository<Balance, BalanceId> {
    public Balance findTopByIdCardNumberOrderByUpdateDateDesc(String cardNumber);
}
