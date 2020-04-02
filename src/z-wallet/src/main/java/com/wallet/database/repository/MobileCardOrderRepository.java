package com.wallet.database.repository;

import com.wallet.database.entity.MobileCardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileCardOrderRepository extends JpaRepository<MobileCardOrder, String> {
}
