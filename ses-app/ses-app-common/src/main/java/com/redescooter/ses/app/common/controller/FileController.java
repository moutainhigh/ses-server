package com.redescooter.ses.app.common.controller;


import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.app.common.service.FileAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: FileController
 * @author: Darren
 * @create: 2019/04/04 11:30
 */
@Slf4j
@Api(tags = {"文件上传"})
@CrossOrigin
@RestController
@RequestMapping(value = "/file", method = RequestMethod.POST)
public class FileController {

    @Autowired
    private FileAppService fileAppService;

    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @param bucket
     * @param file
     * @return
     */
    @RequestMapping(path = {"/upload", "/{bucket}/{dirName}/upload"})
    @ApiOperation(value = "基础图片上传", response = String.class)
    public Response<String> uploadFile(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @PathVariable(required = false) String bucket,
                                       @PathVariable(required = false)String dirName,
                                       MultipartFile file) {

        return new Response(true, fileAppService.uplaod(bucket,dirName, file));
    }
}
