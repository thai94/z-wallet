package com.wallet.files.upload;

import com.wallet.constant.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@RestController
public class UploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/files/upload-file")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        UploadFileResponse response = new UploadFileResponse();
        try {
            String fileName = fileStorageService.storeFile(file);
            response.fileid = fileName;
            response.returncode = ErrorCode.SUCCESS.getValue();
        } catch (IOException ex) {
            ex.printStackTrace();
            response.returncode = ErrorCode.EXCEPTION.getValue();
        }
        return response;
    }
}
