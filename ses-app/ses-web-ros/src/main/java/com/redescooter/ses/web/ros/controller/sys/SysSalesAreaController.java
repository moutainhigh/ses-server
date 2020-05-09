package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sys.SalesAreaService;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private SalesAreaService salesAreaService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "销售区域列表", response = SalesAreaTressResult.class)
    public Response<List<SalesAreaTressResult>> list(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salesAreaService.list(enter));
    }

    @PostMapping(value = "/trees")
    @ApiOperation(value = "销售区域树列表", response = SalesAreaTressResult.class)
    public Response<List<SalesAreaTressResult>> trees(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salesAreaService.trees(enter));
    }
}
