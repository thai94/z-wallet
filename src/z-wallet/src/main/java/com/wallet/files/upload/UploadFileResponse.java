package com.wallet.files.upload;

import com.wallet.entity.BaseResponse;
import lombok.Data;

@Data
public class UploadFileResponse extends BaseResponse {
    public String fileid;
}
