package com.rishi.TheSouthHotel.service;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rishi.TheSouthHotel.exception.OurException;

import java.io.InputStream;

@Service
public class AwsS3Service {

    private  final String bucketName = "south-hotel-images";
    @Value("${aws.S3.access.key}")
    private String accessKey;
    @Value("${aws.S3.secret.key}")
    private String secretKey;

    public String saveImageToS3(MultipartFile photo) {
        String s3LocationImage = null;

        try {
            String s3Filename = photo.getOriginalFilename();

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.EU_NORTH_1) // ✅ Correct region

                    .build();

            InputStream inputStream = photo.getInputStream();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Filename, inputStream, metadata);
            s3Client.putObject(putObjectRequest);

            s3LocationImage = "https://" + bucketName + ".s3.amazonaws.com/" + s3Filename;

            return s3LocationImage;

        } catch (Exception e) {
            e.printStackTrace();
            throw new OurException("Unable to upload image to S3 bucket: " + e.getMessage());
        }
    }
}

	
	

