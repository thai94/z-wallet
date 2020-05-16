package com.wallet.user.update;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
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
import java.util.Optional;

@RestController
public class UpdateUserController {

    @Autowired
    WalletUserRespository walletUserRespository;

    @PostMapping("/um/update-user")
    public BaseResponse updateUser(@RequestBody UpdateUserRequest request) {
        UpdateUserResponse response = new UpdateUserResponse();
        try {
            Optional<WalletUser> walletUserOptional = walletUserRespository.findById(request.userid);
            if(!walletUserOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_USER_DOES_NOT_EXIST.getValue();
                return response;
            }

            WalletUser walletUser = walletUserOptional.get();

            if(StringUtils.isNotEmpty(request.pin)) {
                if(StringUtils.isEmpty(request.key)) {
                    response.returncode = ErrorCode.VALIDATE_KEY_INVALID.getValue();
                    return response;
                }

                PrivateKey privateKey = KeyStoreUtils.readPrivateKey();

                String clientKey = SecurityUtils.decryptRSA(privateKey, request.key);
                SecretKeySpec clientKeySpec = new SecretKeySpec(Base64.getDecoder().decode(clientKey), "AES");

                String pinHmac256 = SecurityUtils.decryptAES(clientKeySpec, request.pin);
                walletUser.pin =  pinHmac256;
            }

            if(StringUtils.isNotEmpty(request.cmnd)) {
                walletUser.cmnd = request.cmnd;
            }

            if(StringUtils.isNotEmpty(request.address)) {
                walletUser.address = request.address;
            }

            if(StringUtils.isNotEmpty(request.dob)) {
                walletUser.dob = request.dob;
            }

            if(StringUtils.isNotEmpty(request.cmndfontimg)) {
                walletUser.cmndFontImg = request.cmndfontimg;
            }

            if(StringUtils.isNotEmpty(request.cmndbackimg)) {
                walletUser.cmndBackImg = request.cmndbackimg;
            }

            if(StringUtils.isNotEmpty(request.avatar)) {
                walletUser.avatar = request.avatar;
            }

            walletUserRespository.save(walletUser);
            response.returncode = ErrorCode.SUCCESS.getValue();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }
}
