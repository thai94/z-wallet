package com.wallet.user.register;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.utils.GenId;
import com.wallet.utils.OtpUtils;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResgisterController {

    @Autowired
    WalletUserRespository walletUserRespository;

    @PostMapping("/um/register")
    public BaseResponse register(@RequestBody UserRegisterRequest dataRequest) {
        UserRegisterResponse response = new UserRegisterResponse();

        try {
            // validate
            if (StringUtils.isEmpty(dataRequest.fullname)) {
                response.returncode = ErrorCode.VALIDATE_FULL_NAME_INVALID.getValue();
                return response;
            }

            if (StringUtils.isEmpty(dataRequest.password)) {
                response.returncode = ErrorCode.VALIDATE_PASSWORD_INVALID.getValue();
                return response;
            }

            if (StringUtils.isEmpty(dataRequest.phone)) {
                response.returncode = ErrorCode.VALIDATE_PHONE_INVALID.getValue();
                return response;
            }

            if(walletUserRespository.findWalletUserByPhone(dataRequest.phone) != null) {
                response.returncode = ErrorCode.VALIDATE_PHONE_DUPLICATE.getValue();
                return response;
            }

            WalletUser walletUser = new WalletUser();

            walletUser.userId = GenId.genId();
            walletUser.fullName = dataRequest.fullname;
            walletUser.password = dataRequest.password;
            walletUser.phone = dataRequest.phone;

            walletUserRespository.save(walletUser);

            response.userid = walletUser.userId;

            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            response.returncode = ErrorCode.EXCEPTION.getValue();
        }

        return response;
    }
}
