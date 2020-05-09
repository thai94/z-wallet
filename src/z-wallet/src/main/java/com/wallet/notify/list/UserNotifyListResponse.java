package com.wallet.notify.list;

import com.wallet.entity.BaseResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserNotifyListResponse extends BaseResponse {
    public List<UserNotifyEntity> histories = new ArrayList<>();
}
