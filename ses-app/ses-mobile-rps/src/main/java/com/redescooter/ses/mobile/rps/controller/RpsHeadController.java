package com.redescooter.ses.mobile.rps.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.RpsHeadService;
import com.redescooter.ses.mobile.rps.vo.materialqc.RpsHeadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:RpsHeadController
 * @description: RpsHeadController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 10:06
 */
@Log4j2
@Api(tags = {"Rps首页"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rps/head")
public class RpsHeadController {

    @Autowired
    private RpsHeadService rpsHeadService;

    @IgnoreLoginCheck
    @PostMapping(value = "/index")
    @ApiOperation(value = "Rps首页数据", response = RpsHeadResult.class)
    public Response<RpsHeadResult> accountList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rpsHeadService.rpsHead(enter));
    }

}
