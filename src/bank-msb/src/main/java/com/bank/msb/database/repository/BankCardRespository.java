package com.bank.msb.database.repository;

import com.bank.msb.database.entity.BankCard;
import com.bank.msb.database.entity.BankCardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRespository extends JpaRepository<BankCard, BankCardId> {
}
