package com.wallet.pay.history;

import com.wallet.entity.BaseResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionHistoryResponse extends BaseResponse {

    public List<TransactionEntity> histories = new ArrayList<>();
}
