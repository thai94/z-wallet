package com.wallet.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "user_notify")
@Data
public class UserNotify {

    @Id
    public long notifyId;
    public String userId;
    public int serviceType;
    public String title;
    public String content;
    public int status; // 1. Not Read, 2. Seen, 3. Deleted
    public long createDate;
}
