package com.wallet.bankmapping.list;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.BankMapping;
import com.wallet.database.repository.BankMappingRespository;
import com.wallet.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetLinkedBankListController {

    @Autowired
    BankMappingRespository bankMappingRespository;

    @PostMapping("/bank-mapping/list")
    public BaseResponse list(@RequestBody GetLinkedListRequest request) {
        GetLinkedBankListResponse response = new GetLinkedBankListResponse();
        try {
            // validate
            if (StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }
            List<BankMapping> bankMappings = bankMappingRespository.findAllByIdUserid(request.userid);
            if(bankMappings.isEmpty()) {
                response.returncode = ErrorCode.SUCCESS.getValue();
                return response;
            }

            for (BankMapping cardMapping: bankMappings) {
                BankInfo card = new BankInfo();
                card.bankcode = cardMapping.bankCode;
                card.cardname = cardMapping.cardName;
                card.f6cardno = cardMapping.f6cardno;
                card.l4cardno = cardMapping.l4cardno;

                response.cards.add(card);
            }
            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
