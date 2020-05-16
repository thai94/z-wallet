package com.wallet.user.register;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import com.wallet.utils.GenId;
import com.wallet.utils.KeyStoreUtils;
import com.wallet.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.util.Base64;

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

            if (StringUtils.isEmpty(dataRequest.pin)) {
                response.returncode = ErrorCode.VALIDATE_PIN_INVALID.getValue();
                return response;
            }

            if (StringUtils.isEmpty(dataRequest.phone)) {
                response.returncode = ErrorCode.VALIDATE_PHONE_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(dataRequest.key)) {
                response.returncode = ErrorCode.VALIDATE_KEY_INVALID.getValue();
                return response;
            }

            if(walletUserRespository.findWalletUserByPhone(dataRequest.phone) != null) {
                response.returncode = ErrorCode.VALIDATE_PHONE_DUPLICATE.getValue();
                return response;
            }

            WalletUser walletUser = new WalletUser();

            PrivateKey privateKey = KeyStoreUtils.readPrivateKey();

            String clientKey = SecurityUtils.decryptRSA(privateKey, dataRequest.key);
            SecretKeySpec clientKeySpec = new SecretKeySpec(Base64.getDecoder().decode(clientKey), "AES");

            String pinHmac256 = SecurityUtils.decryptAES(clientKeySpec, dataRequest.pin);

            walletUser.userId = GenId.genId();
            walletUser.fullName = dataRequest.fullname;
            walletUser.pin = pinHmac256;
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
