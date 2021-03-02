package com.redescooter.ses.tool.utils.file;

import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @ClassNameFileUpload
 * @Description  上传文件的工具类
 * @Author Aleks
 * @Date2020/6/17 20:52
 * @Version V1.0
 **/
public class FileUtil {

    /**
     * 上传文件到指定路径
     * @param mFile 要上传的文件
     * @param path  指定路径
     */
    public static String uploadFile(MultipartFile mFile, String path) {
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
        } catch (Exception e) {
            System.out.println("----------" + path +"文件上传失败————————");
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

}
