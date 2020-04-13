package com.redescooter.ses.tool.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.*;
@Slf4j

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 6:28 下午
 * @ClassName: DownloadFileUtil
 * @Function: TODO
 */
public class DownloadFileUtil {

    public static final String separator = File.separator;

    /**
     * 下载样表
     * @param filePath 文件目录
     * @param newName  下载的展示文件名
     * @return 响应
     */
    public static ResponseEntity<InputStreamResource> download(String filePath, String newName) {
        String path = null;
        ResponseEntity<InputStreamResource> response = null;
        try {
            path = filePath;

            InputStream inputStream = new FileInputStream(path);
            //File file = new File(path);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition",
                    "attachment; filename="
                            + new String(newName.getBytes("gbk"), "iso8859-1") + ".xlsx");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            response = ResponseEntity.ok().headers(headers)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(inputStream));
        } catch (FileNotFoundException e1) {
            log.error("找不到指定的文件", e1);
        } catch (IOException e) {
            log.error("获取不到文件流", e);
        }
        return response;
    }
}
