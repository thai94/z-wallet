package com.bank.sacombank.database.repository;

import com.bank.sacombank.database.entity.WalletMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletMappingRespository extends JpaRepository<WalletMapping, String> {
}
