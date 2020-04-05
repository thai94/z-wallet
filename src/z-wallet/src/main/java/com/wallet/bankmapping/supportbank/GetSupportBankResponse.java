package com.wallet.bankmapping.supportbank;

import com.wallet.entity.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class GetSupportBankResponse extends BaseResponse {
    public List<BankInfo> banks = new ArrayList<>();
}
