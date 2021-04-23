package com.redescooter.ses.admin.dev.service.impl;

import com.redescooter.ses.admin.dev.service.FileUploadService;
import com.redescooter.ses.api.foundation.vo.app.FileUploadResultDTO;
import com.redescooter.ses.tool.utils.file.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author assert
 * @date 2020/12/7 14:25
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${fileUpload.path}")
    private String updatePackageUploadPath;

    @Value("${fileUpload.download}")
    private String downloadPath;

    @Override
    public FileUploadResultDTO fileUpload(MultipartFile file) {
        FileUploadResultDTO result = new FileUploadResultDTO();

        String fileName = System.currentTimeMillis() + ".apk";
        String path = updatePackageUploadPath + fileName;
        // 调方法 上传文件
        FileUtil.uploadFile(file, path);
        result.setFileUrl(downloadPath + fileName);
        result.setFileSize(String.valueOf(file.getSize() / 1024 / 1024));
        return result;
    }


    @SneakyThrows
    @Override
    public void downLoadFile(String fileName, HttpServletResponse response, HttpServletRequest request) {
            log.info("准备开始下载！！！！！！！！！！");
            String apkurl=updatePackageUploadPath + fileName;
            log.info("文件的路径！！！！！！！！！！="+apkurl);
            FileUtil.downLoadFile(apkurl,response);
    }





}
