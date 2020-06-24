package com.redescooter.ses.tool.demo;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

/**
 * @ClassNameS3Util
 * @Description
 * @Author Aleks
 * @Date2020/6/23 10:59
 * @Version V1.0
 **/
public class S3Util {
    /**
     * 初始化aws文件的服务
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(S3Util.class);
    private static AmazonS3 s3 = null;
    private static final String LINK_STR = "/";
    private static final String FILE_POSTFIX = ".csv";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String FILE_PREFIX = "dt";
    private static final String FILE_LINK_STR = "=";

    private static String bucketName = "redescooter";


    /***
     * s3初始化方法
     * */
    private static void initS3() {
        if(s3 == null){
            try {
                BasicAWSCredentials awsCredentials = new BasicAWSCredentials("AKIATFAR2U3Z3RM544U4", "q/XRUjjEQYH1XeLTBNoax8wFlRn0llO4kuSDyavm");
//                s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion("eu-west-3").build();
                s3 = new AmazonS3Client(awsCredentials);
            } catch (Exception e) {
                LOGGER.error("s3初始化系统异常", e);
            }
        }
    }

    /**
     * 上传文件
     * @throws IOException
     */
    public  static  boolean uploadFile(File file, String bn,Date now) {
        // 判断s3桶是否存在
        initS3();
        try{
            s3.putObject(new PutObjectRequest(bucketName, file.getName(), file));
        } catch (Exception e) {
            LOGGER.error("Upload an object to your bucket error:{}" + e);
            System.out.println("上传失败！！！");
            return false;
        }
        System.out.println("上传成功！！！");
        return true;
    }


    public static void main(String[] args) throws IOException {
        File file = new File("D:\\Chrom\\ChromDown\\Redis使用规范.docx");
        String bucketName = "redescooter";
        uploadFile(file,bucketName,new Date());
    }




    public static File createSampleFile(String content,Date now) throws IOException {
        File file = new File("");
        file.deleteOnExit();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file),CHARSET_NAME);
        writer.write(content);
        writer.close();
        return file;
    }

    /**
     * 多线程写文件
     */

    public  static void saveFileToS3(String content, String bucketName) {
        try {
            Date now = new Date();
            File tempFile = createSampleFile(content, now);
            if(null!=tempFile){
                boolean result = uploadFile(tempFile, bucketName, now);
                if(result){
                    tempFile.delete();
                }
            }else{
                LOGGER.info("创建本地文件失败");
            }
        } catch (IOException e) {
            LOGGER.error("写入s3文件系统异常:{}" + e);
        }
    }

}
