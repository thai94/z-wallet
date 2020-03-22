package com.bank.msb.database.repository;

import com.bank.msb.database.entity.BankCard;
import com.bank.msb.database.entity.BankCardId;
import com.bank.msb.database.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRespository extends JpaRepository<Customer, String> {
}
