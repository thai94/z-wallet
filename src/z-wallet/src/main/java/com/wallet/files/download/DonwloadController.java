package com.wallet.files.download;

import com.wallet.files.upload.FileStorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

@RestController
public class DonwloadController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping(value = "/files/download/{fileid:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileid, HttpServletRequest request) throws MalformedURLException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileid);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value = "/files/view-image/{fileid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] view(@PathVariable String fileid, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileid);

        InputStream inputStream = new FileInputStream(resource.getFile());

        return IOUtils.toByteArray(inputStream);
    }
}
