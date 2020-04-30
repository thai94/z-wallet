package com.wallet.database.repository;

import com.wallet.database.entity.WalletUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletUserRespository extends JpaRepository<WalletUser, String>, JpaSpecificationExecutor<WalletUser> {
    WalletUser findWalletUserByPhone(String phone);
    WalletUser findWalletUserByPhoneAndPin(String phone, String password);
    WalletUser findWalletUserByUserIdAndPin(String userId, String password);
}
