package com.redescooter.ses.tool.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassNameFileUpload
 * @Description  上传文件的工具类
 * @Author Aleks
 * @Date2020/6/17 20:52
 * @Version V1.0
 **/
@Slf4j
public class FileUtil {

    /**
     * 上传文件到指定路径
     * @param mFile 要上传的文件
     * @param path  指定路径
     */
    public static String uploadFile(MultipartFile mFile, String path) {
        log.info("----------" + path +"文件准备上传————————");
        try {
            InputStream in = mFile.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            OutputStream out = new FileOutputStream(path);
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            log.info("----------" + path +"文件上传完成————————");
        } catch (Exception e) {
            log.info("----------" + path +"文件上传失败*****原因："+e.getMessage());
            e.printStackTrace();
        }
        return path;
    }


    public static MultipartFile fileToMulFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
        return multipartFile;
    }


    /**
     * 通过地址  下载文件
     * @param apkurl
     * @param response
     * @throws IOException
     */
    public static void downLoadFile(String apkurl, HttpServletResponse response) {
        try {
            File file = new File(apkurl);
            if (file.exists()) {//文件存在
                InputStream in = new FileInputStream(file);
                OutputStream os = response.getOutputStream();
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + new String((file.getName()).getBytes("GB2312"), "iso8859-1"));//此行代码可避免中文文件名乱码问题
                response.addHeader("Content-Length", file.length() + "");
                response.setContentType("application/octet-stream");
                int data = 0;
                while ((data = in.read()) != -1) {
                    os.write(data);
                }
                log.info("下载文件完成，准备关闭资源！！！！！！！！！！");
                os.close();
                in.close();
                os.flush();
                log.info("**********资源关闭完成*********************");
            } else {
                log.info("文件不存在！！！！！！！！！！");
                response.setCharacterEncoding("utf-8");
                response.getWriter().print("<script>alert(\"Le fichier n'existe pas!\");window.history.go(-1);</script>");
            }
        } catch (Exception e) {
            log.info("下载文件失败，原因："+e.getMessage());
        }
    }

}
