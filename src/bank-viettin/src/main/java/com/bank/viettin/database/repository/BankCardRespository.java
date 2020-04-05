package com.bank.viettin.database.repository;

import com.bank.viettin.database.entity.BankCard;
import com.bank.viettin.database.entity.BankCardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRespository extends JpaRepository<BankCard, BankCardId> {
}
