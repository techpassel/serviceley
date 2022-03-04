package com.tp.serviceley.server.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.tp.serviceley.server.exception.BackendException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
public class FileUploadService {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Autowired
    private CommonService commonService;

    @Value("${bucketName}")
    private String bucketName;

    public String uploadFile(String keyName, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new BackendException("Cannot upload empty file");
        }
        //Check if the file is an image
        String mimeType = file.getContentType();
        if (!mimeType.contains("image/") && !mimeType.equals("application/pdf")) {
            throw new BackendException("Unsupported file type for.Only images and pdf are supported");
        }

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            String path = keyName != "" ? String.format("%s/%s", bucketName, keyName) : bucketName;
            String fileExtension = commonService.getFileExtension(file.getOriginalFilename());
            String fileNewName = (new Date()).getTime() + "-" + RandomStringUtils.randomAlphanumeric(16).toLowerCase() + fileExtension;
            amazonS3Client.putObject(path, fileNewName, file.getInputStream(), metadata);
            return keyName != "" ? keyName + "/" + fileNewName : fileNewName;
        } catch (IOException ioe) {
            String err = "IOException: " + ioe.getMessage();
            log.error(err);
            throw new BackendException("Error in uploading " + keyName + ": " + err);
        } catch (AmazonServiceException serviceException) {
            String err = "AmazonServiceException: " + serviceException.getMessage();
            log.error(err);
            throw new BackendException("Error in uploading " + keyName + ": " + err);
        } catch (AmazonClientException clientException) {
            String err = "AmazonClientException: " + clientException.getMessage();
            log.error(err);
            throw new BackendException("Error in uploading " + keyName + ": " + err);
        }
    }
}
