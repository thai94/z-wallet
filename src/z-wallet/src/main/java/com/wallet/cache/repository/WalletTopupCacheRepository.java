package com.wallet.cache.repository;

import com.wallet.cache.entity.WalletTopupEntity;
import com.wallet.cache.entity.WithdrawOrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTopupCacheRepository extends CrudRepository<WalletTopupEntity, String> {
}
