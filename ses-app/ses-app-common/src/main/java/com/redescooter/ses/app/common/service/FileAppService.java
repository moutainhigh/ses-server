package com.redescooter.ses.app.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


public interface FileAppService {

    String uplaod(String bucket, String dirName, MultipartFile file);

    InputStream download(String url);
}
