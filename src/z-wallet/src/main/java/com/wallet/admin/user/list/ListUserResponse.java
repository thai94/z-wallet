package com.wallet.admin.user.list;

import com.wallet.database.entity.WalletUser;
import com.wallet.entity.BaseResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListUserResponse extends BaseResponse {
    public List<WalletUser> users = new ArrayList<>();
}
