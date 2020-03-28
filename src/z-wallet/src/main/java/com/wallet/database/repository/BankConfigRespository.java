package com.wallet.database.repository;

import com.wallet.database.entity.BankConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankConfigRespository extends JpaRepository<BankConfig, String> {
}
