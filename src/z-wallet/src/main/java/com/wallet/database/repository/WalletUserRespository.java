package com.wallet.database.repository;

import com.wallet.database.entity.WalletUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletUserRespository extends JpaRepository<WalletUser, String> {
    WalletUser findWalletUserByPhone(String phone);
    WalletUser findWalletUserByPhoneAndPin(String phone, String password);
}
