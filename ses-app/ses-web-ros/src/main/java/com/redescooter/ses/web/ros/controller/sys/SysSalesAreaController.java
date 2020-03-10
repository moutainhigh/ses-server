package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SysSalesAreaController
 * @Author Jerry
 * @date 2020/03/10 15:46
 * @Description:
 */

@Api(tags = {"销售区域管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/sales/area/")
public class SysSalesAreaController {

    @PostMapping(value = "/list")
    @ApiOperation(value = "销售区域列表", response = GeneralResult.class)
    public Response<PageResult<GeneralResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
