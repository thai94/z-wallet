package com.wallet.database.repository;

import com.wallet.database.entity.Wallet;
import com.wallet.database.entity.WalletId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, WalletId> {
    public Wallet findTopByIdUserIdOrderByUpdateDateDesc(String userId);
}
