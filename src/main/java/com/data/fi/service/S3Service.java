package com.data.fi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;


    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(File file, String keyName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());
        PutObjectRequest request = new PutObjectRequest(bucketName, keyName, file);
        amazonS3.putObject(request);
        return amazonS3.getUrl(bucketName, keyName).toString(); // Returns the URL of the uploaded file
    }
}
