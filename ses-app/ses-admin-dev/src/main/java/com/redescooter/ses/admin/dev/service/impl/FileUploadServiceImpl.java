package com.redescooter.ses.admin.dev.service.impl;

import com.redescooter.ses.admin.dev.service.FileUploadService;
import com.redescooter.ses.api.foundation.vo.app.FileUploadResultDTO;
import com.redescooter.ses.tool.utils.file.FileUtil;
import com.redescooter.ses.tool.utils.thread.ThreadPoolExecutorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author assert
 * @date 2020/12/7 14:25
 */
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

        result.setFileUrl(downloadPath + fileName);
        result.setFileSize(String.valueOf(file.getSize() / 1024 / 1024));

        /**
         * 上传文件到服务器
         */
        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            FileUtil.uploadFile(file, path);
        });
        return result;
    }

}
