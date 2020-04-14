package com.redescooter.ses.mobile.rps.controller;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.material.MaterialService;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:MaterialController
 * @description: MaterialController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 10:11
 */
@Log4j2
@Api(tags = {"来料质检模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rps/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "来料质检列表", response = MaterialQcListResult.class)
    public Response<PageResult<MaterialQcListResult>> list(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(materialService.list(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "来料质检列表", response = MaterialQcListResult.class)
    public Response<PageResult<MaterialQcListResult>> failList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(materialService.failList(enter));
    }
}
