package com.wallet.transactionhistory.list;

import com.wallet.entity.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryListResponse extends BaseResponse {

    public List<TransactionHistoryEntity> histories = new ArrayList<>();
}
