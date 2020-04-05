package com.bank.msb.database.repository;

import com.bank.msb.database.entity.Customer;
import com.bank.msb.database.entity.WalletMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletMappingRespository extends JpaRepository<WalletMapping, String> {
}
