package com.redescooter.ses.app.common.service.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.redescooter.ses.api.common.enums.oss.ProtocolEnums;
import com.redescooter.ses.api.common.exception.BaseException;
import com.redescooter.ses.app.common.service.FileAppService;
import com.redescooter.ses.starter.common.config.OssConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@Slf4j
@Service
public class FileAppServiceImpl implements FileAppService {

    @Autowired
    private OssConfig ossConfig;

    @Override
    public String uplaod(String bucket, String dirName, MultipartFile file) {
        OSSClient ossClient = null;
        String fileName = null;
        int mb = 0;
        try {
            if (file.getSize() > ossConfig.getMaxFileSize()) {
                mb = ossConfig.getMaxFileSize() / 1024 / 1024;
                throw new IllegalAccessException("The file size cannot exceed " + mb + "MB");
            }
            String full = file.getOriginalFilename();
            String suffix = full.substring(full.indexOf("."), full.length());
            Random random = new Random();
            if (StringUtils.isNotEmpty(dirName)) {
                fileName = dirName + "/" + System.currentTimeMillis() + random.nextInt(10000) + suffix;
            } else {
                fileName = System.currentTimeMillis() + random.nextInt(10000) + suffix;
            }
            if (StringUtils.isEmpty(bucket)) {
                bucket = ossConfig.getDefaultBucketName();
            }

            //oss 开启https 上传 添加其他配置 参看 ClientConfiguration 对象
            ClientConfiguration conf = new ClientConfiguration();
            conf.setProtocol(ProtocolEnums.getProtocol(ossConfig.getProtocol()));

            ossClient = new OSSClient(ossConfig.getInternalEndpoint(), ossConfig.getAccessKeyId(),
                    ossConfig.getSecretAccesskey(), conf);

            PutObjectResult result = ossClient.putObject(ossConfig.getDefaultBucketName(), fileName,
                    file.getInputStream());
        } catch (IOException e) {
            throw new BaseException(BaseException.DEFAULE_ERRORCODE, "upload file failure.", e);
        } catch (IllegalAccessException e) {
            throw new BaseException(BaseException.DEFAULE_ERRORCODE, "The file size cannot exceed " + mb + "MB");
        } finally {
            ossClient.shutdown();
        }
        return "https://" + bucket + "." + ossConfig.getPublicEndpointDomain() + "/" + fileName;
    }

    @Override
    public InputStream download(String url) {
        InputStream input = null;
        //oss 开启https 下载 添加其他配置 参看 ClientConfiguration 对象
        ClientConfiguration conf = new ClientConfiguration();
        conf.setProtocol(ProtocolEnums.getProtocol(ossConfig.getProtocol()));

        OSSClient ossClient = new OSSClient(ossConfig.getInternalEndpoint(), ossConfig.getAccessKeyId(),
                ossConfig.getSecretAccesskey(),conf);
        if (url.indexOf("//") >= 0) {
            url = url.split("//")[1];
        }
        String bucketName = url.split("\\.")[0];
        String[] arrays = url.split("/");
        String key = arrays[arrays.length - 1];
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        if (ossObject != null) {
            input = ossObject.getObjectContent();
        }
        return input;
    }


}
