package com.redescooter.ses.admin.dev.service.impl;

import com.redescooter.ses.admin.dev.service.FileUploadService;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.app.FileUploadResultDTO;
import com.redescooter.ses.tool.utils.file.FileUtil;
import com.redescooter.ses.tool.utils.thread.ThreadPoolExecutorUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

        result.setFileUrl(downloadPath + fileName);
        result.setFileSize(String.valueOf(file.getSize() / 1024 / 1024));

        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            FileUtil.uploadFile(file, path);
        });
        return result;
    }


    @SneakyThrows
    @Override
    public GeneralResult downLoadFile(String fileName, HttpServletResponse response, HttpServletRequest request) {
        try {
            log.info("准备开始下载！！！！！！！！！！");
//            String apkurl=request.getParameter(updatePackageUploadPath + fileName);//fileUrl是从前台传过来的文件下载路径（相对路径）
            String apkurl=updatePackageUploadPath + fileName;//fileUrl是从前台传过来的文件下载路径（相对路径）
            log.info("文件的路径！！！！！！！！！！="+apkurl);
       //     String filepath = request.getSession().getServletContext().getRealPath("") + apkurl;//filepath为下载文件的绝对路径
            File file = new File(apkurl);
            if (file.exists()) {//文件存在
                InputStream in = new FileInputStream(file);
                OutputStream os = response.getOutputStream();
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=" + new String((file.getName()).getBytes("GB2312"),"iso8859-1")) ;//此行代码可避免中文文件名乱码问题
                response.addHeader("Content-Length", file.length() + "");
                response.setContentType("application/octet-stream");
                int data = 0;
                while ((data = in.read()) != -1) {
                    os.write(data);
                }
                os.close();
                in.close();
            }else{
                log.info("文件不存在！！！！！！！！！！");
                response.setCharacterEncoding("utf-8");
                response.getWriter().print("<script>alert(\"文件不存在!\");window.history.go(-1);</script>");
            }
        } catch (Exception ignored) {
        }
        return null;

    }





}
