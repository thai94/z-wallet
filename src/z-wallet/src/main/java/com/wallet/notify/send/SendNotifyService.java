package com.wallet.notify.send;

import com.wallet.database.entity.UserNotify;
import com.wallet.database.repository.UserNotifyRepository;
import com.wallet.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendNotifyService {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Autowired
    UserNotifyRepository userNotifyRepository;

    public boolean send(UserNotify notify) {

        try {
            notify.status = 1;
            notify.createDate = System.currentTimeMillis();
            userNotifyRepository.save(notify);
            // send websocket msg
            webSocket.convertAndSend("/ws/topic/notify_" + notify.userId, GsonUtils.toJsonString(notify));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
