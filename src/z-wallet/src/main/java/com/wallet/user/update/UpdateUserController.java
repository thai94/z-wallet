package com.wallet.user.update;

import com.wallet.constant.ErrorCode;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

            if(!StringUtils.isEmpty(request.pin)) {
                walletUser.pin =  request.pin;
            }

            if(!StringUtils.isEmpty(request.cmnd)) {
                walletUser.cmnd = request.cmnd;
            }

            if(!StringUtils.isEmpty(request.address)) {
                walletUser.address = request.address;
            }

            if(!StringUtils.isEmpty(request.dob)) {
                walletUser.dob = request.dob;
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
