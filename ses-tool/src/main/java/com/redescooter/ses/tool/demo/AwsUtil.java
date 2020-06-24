package com.redescooter.ses.tool.demo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @ClassNameAwsUtil
 * @Description
 * @Author Aleks
 * @Date2020/6/22 15:46
 * @Version V1.0
 **/
public class AwsUtil {

    public static void awsTest() {

        String bucket_name = "redescooter";
        String file_path = "C:\\Users\\Admin\\Desktop\\自建\\重构大致说明.doc";
        String key_name = Paths.get(file_path).getFileName().toString();

//        System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("eu-west-3").build();
        try {
            s3.putObject(bucket_name, key_name, new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }

    public static void main(String[] args) throws IOException {
        Regions clientRegion = Regions.DEFAULT_REGION;
        String bucketName = "redescooter";
        String stringObjKeyName = "test";
        String fileObjKeyName = "test123";
        String fileName = "D:\\Chrom\\ChromDown\\Redis使用规范.docx";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion("eu-west-3")
                    .build();

            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, new File(fileName));

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the clOient
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }



}
