package com.redescooter.ses.admin.dev.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.app.FileUploadResultDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传业务层接口
 * @author assert
 * @date 2020/12/7 14:22
 */
public interface FileUploadService {

    /**
     * 上传应用更新包
     * @param file
     * @return com.redescooter.ses.api.foundation.vo.app.FileUploadResultDTO
     * @author assert
     * @date 2020/12/4
     */
    FileUploadResultDTO fileUpload(MultipartFile file);

    /**
     * 安装包的下载
     * @param fileName
     * @param response
     * @return
     */
    void downLoadFile(String fileName, HttpServletResponse response, HttpServletRequest request);

}
