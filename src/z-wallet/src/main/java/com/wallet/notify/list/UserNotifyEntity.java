package com.wallet.notify.list;

import com.wallet.database.entity.UserNotify;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserNotifyEntity {

    public long notifyId;
    public String userId;
    public int serviceType;
    public String title;
    public String content;
    public String time;
    public int status;
    public long timemilliseconds;

    public UserNotifyEntity(UserNotify notify) {
        this.notifyId = notify.notifyId;
        this.userId = notify.userId;
        this.serviceType = notify.serviceType;
        this.title = notify.title;
        this.content = notify.content;
        this.status = notify.status;

        Date date = new Date();
        date.setTime(notify.createDate);
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        this.time = formattedDate;
        this.timemilliseconds = notify.createDate;
    }
}
