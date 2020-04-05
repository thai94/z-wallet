package com.bank.viettin.database.repository;

import com.bank.viettin.database.entity.WalletMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletMappingRespository extends JpaRepository<WalletMapping, String> {
}
