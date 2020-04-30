package com.wallet.notify.list;

import com.wallet.database.entity.UserNotify;
import com.wallet.entity.BaseResponse;
import com.wallet.pay.history.TransactionEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserNotifyListResponse extends BaseResponse {
    public List<UserNotifyEntity> histories = new ArrayList<>();
}
