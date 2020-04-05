package com.bank.sacombank.database.repository;

import com.bank.sacombank.database.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRespository extends JpaRepository<Customer, String> {
}
