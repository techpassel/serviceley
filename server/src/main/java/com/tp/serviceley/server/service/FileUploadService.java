package com.tp.serviceley.server.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.tp.serviceley.server.exception.BackendException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FileUploadService {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Autowired
    private CommonService commonService;

    @Value("${bucketPath}")
    private String bucketPath;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${bucketDedicatedFolder}")
    private String bucketDedicatedFolder;

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
            String path = keyName != "" ? String.format("%s/%s", bucketPath, keyName) : bucketPath;
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

    public void deleteFile(String keyName) {
        try {
            amazonS3Client.deleteObject(bucketPath, keyName);
        } catch (Exception e) {
            log.error("Error in deleting file - " + keyName + ". Error details - " + e.getMessage());
            throw new BackendException("Error in deleting file - " + keyName);
        }
    }

    public void deleteFolderAllFiles(String folderPrefix) {
        try {
            //Listing objects within the path inside "keyName"
            ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName)
                    .withPrefix(bucketDedicatedFolder + folderPrefix).withMaxKeys(2);
            // Note that here we have used 'bucketName' and 'bucketDedicatedFolder' separately and not the 'bucketPath'
            // As withBucketName() requires raw bucket name i.e. we should not pass inner dedicated folder path in it
            // otherwise it will throw error.
            ListObjectsV2Result result;
            List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>();
            do {
                result = amazonS3Client.listObjectsV2(req);
                for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                    keys.add(new DeleteObjectsRequest.KeyVersion(objectSummary.getKey()));
                }
                // If there are more than maxKeys keys in the bucket, get a continuation token and list the next objects.
                String token = result.getNextContinuationToken();
                req.setContinuationToken(token);
            } while (result.isTruncated());
            //Deleting all objects from bucket
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
                    .withKeys(keys)
                    .withQuiet(false);
            amazonS3Client.deleteObjects(multiObjectDeleteRequest);
            //If you want to check the deleted objects you can do it as follows
            //DeleteObjectsResult delObjRes = amazonS3Client.deleteObjects(multiObjectDeleteRequest);
            //int successfulDeletes = delObjRes.getDeletedObjects().size();
        } catch (Exception e) {
            log.error("Error in deleting folder: "+e.getMessage());
            throw new BackendException("Error in deleting folder - " + folderPrefix);
        }
    }
}
