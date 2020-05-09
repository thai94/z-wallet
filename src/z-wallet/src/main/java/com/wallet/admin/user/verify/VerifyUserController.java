package com.wallet.admin.user.verify;

import com.wallet.constant.ErrorCode;
import com.wallet.constant.Service;
import com.wallet.database.entity.UserNotify;
import com.wallet.database.entity.WalletUser;
import com.wallet.database.repository.WalletUserRespository;
import com.wallet.notify.send.SendNotifyService;
import com.wallet.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VerifyUserController {

    @Autowired
    WalletUserRespository walletUserRespository;

    @Autowired
    SendNotifyService sendNotifyService;

    @PostMapping("/admin/user/verify")
    public VerifyUserResponse verifyUser(@RequestBody VerifyUserRequest request) {
        VerifyUserResponse response = new VerifyUserResponse();
        try {
            if(StringUtils.isEmpty(request.userid)) {
                response.returncode = ErrorCode.VALIDATE_USER_ID_INVALID.getValue();
                return response;
            }

            if(request.status != 1 && request.status != 2) {
                response.returncode = ErrorCode.VALIDATE_USER_VERIFY_STATUS_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(request.comment)) {
                response.returncode = ErrorCode.VALIDATE_USER_VERIFY_COMMENT_INVALID.getValue();
                return response;
            }
            Optional<WalletUser> userOptional = walletUserRespository.findById(request.userid);
            if(!userOptional.isPresent()) {
                response.returncode = ErrorCode.CHECK_USER_DOES_NOT_EXIST.getValue();
                return response;
            }

            WalletUser user = userOptional.get();
            if(request.status == 2) {
                user.setVerify(2); // rejected\
                walletUserRespository.save(user);
                response.returncode = ErrorCode.SUCCESS.getValue();

                // notify
                UserNotify notify = new UserNotify();
                notify.notifyId = System.currentTimeMillis();
                notify.userId = request.userid;
                notify.serviceType = Service.VERIFY_USER.getKey();
                notify.title = "Xác thực thông tin tài khoản thất bại";

                VerifyUserNotifyTemplate verifyUserNotifyTemplate = new VerifyUserNotifyTemplate();
                verifyUserNotifyTemplate.status = request.status;
                verifyUserNotifyTemplate.comment = request.comment;

                notify.content = GsonUtils.toJsonString(verifyUserNotifyTemplate);
                notify.createDate = System.currentTimeMillis();
                sendNotifyService.send(notify);
                return response;
            }

            // verified case
            // simple to check whether if all information is filled in. AND how quality of information which user inputed must be verify by admin because we don't doing automation at here.
            if(StringUtils.isEmpty(user.phone)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_PHONE_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.address)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_ADDRESS_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.dob)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_DOB_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.fullName)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_FULLNAME_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.cmnd)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_CMND_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.pin)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_PIN_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.cmndFontImg)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_CMND_FRONT_IMG_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.cmndBackImg)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_CMND_BACK_IMG_INVALID.getValue();
                return response;
            }

            if(StringUtils.isEmpty(user.avatar)) {
                response.returncode = ErrorCode.CHECK_VERIFY_USRER_AVATAR_IMG_INVALID.getValue();
                return response;
            }

            user.setVerify(1); // verified
            walletUserRespository.save(user);
            response.returncode = ErrorCode.SUCCESS.getValue();


            // notify
            UserNotify notify = new UserNotify();
            notify.notifyId = System.currentTimeMillis();
            notify.userId = request.userid;
            notify.serviceType = Service.VERIFY_USER.getKey();
            notify.title = "Xác thực thông tin tài khoản thành công";

            VerifyUserNotifyTemplate verifyUserNotifyTemplate = new VerifyUserNotifyTemplate();
            verifyUserNotifyTemplate.status = request.status;
            verifyUserNotifyTemplate.comment = request.comment;

            notify.content = GsonUtils.toJsonString(verifyUserNotifyTemplate);
            notify.createDate = System.currentTimeMillis();
            sendNotifyService.send(notify);

            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
            return response;
        }
    }


}
