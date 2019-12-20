package com.redescooter.ses.app.common.controller;


import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.app.common.service.FileAppService;
import com.redescooter.ses.tool.utils.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(path = {"/upload", "/{bucket}/upload"})
    @ApiOperation(value = "基础图片上传", response = String.class)
    public Response<String> uploadFile(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @PathVariable(required = false) String bucket,
                                       MultipartFile file) {

        return new Response(true, fileAppService.uplaod(bucket, file));
    }
}
