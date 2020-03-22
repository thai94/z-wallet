package com.wallet.bankmapping.list;

import com.wallet.entity.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class GetLinkedBankListResponse extends BaseResponse {
    public List<BankInfo> cards = new ArrayList<>();
}
