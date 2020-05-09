package com.wallet.database.repository;

import com.wallet.database.entity.UserNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotifyRepository extends JpaRepository<UserNotify, Long> {
}
