package com.bank.sacombank.database.repository;

import com.bank.sacombank.database.entity.BankCard;
import com.bank.sacombank.database.entity.BankCardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRespository extends JpaRepository<BankCard, BankCardId> {
}
