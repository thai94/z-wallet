package com.wallet.transactionhistory.one;

import com.wallet.entity.BaseResponse;
import com.wallet.transactionhistory.list.TransactionHistoryEntity;
import lombok.Data;

@Data
public class TransactionHistoryResponse extends BaseResponse {
    public TransactionHistoryEntity data = new TransactionHistoryEntity();
}
