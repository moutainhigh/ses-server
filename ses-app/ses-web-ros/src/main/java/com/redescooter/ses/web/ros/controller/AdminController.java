package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.admin.DeleteRosLoginDateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Api(tags = {"超管删除业务"})
@CrossOrigin
@RestController
@RequestMapping(value = "/admin/")
public class AdminController {
    @Autowired
    private DeleteRosLoginDateService deleteRosLoginDateService;

    @PostMapping(value = "/accountList")
    @ApiOperation(value = "账户列表", response = GeneralResult.class)
    public Response<GeneralResult> accountList(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        deleteRosLoginDateService.deleteRosLoginDate(enter);
        return new Response<>(new GeneralResult());
    }

}
