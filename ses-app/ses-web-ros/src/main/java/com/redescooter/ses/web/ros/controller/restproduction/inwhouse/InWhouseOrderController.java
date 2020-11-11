package com.redescooter.ses.web.ros.controller.restproduction.inwhouse;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseSaveOrUpdateEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassNameAllocateOrderController
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:37
 * @Version V1.0
 **/
@Api(tags = {"入库单控制层"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/inWhouse/order")
public class InWhouseOrderController {


    @Autowired
    private InWhouseService inWhouseService;


    @PostMapping(value = "/list")
    @ApiOperation(value = "入库单列表", response = InWhouseListResult.class)
    public Response<PageResult<InWhouseListResult>> inWhouseList(@ModelAttribute @ApiParam("请求参数") InWhouseListEnter enter) {
        return new Response<>(inWhouseService.inWhouseList(enter));
    }


    @PostMapping(value = "/save")
    @ApiOperation(value = "入库单新增", response = GeneralResult.class)
    public Response<GeneralResult> inWhouseSave(@ModelAttribute @ApiParam("请求参数") InWhouseSaveOrUpdateEnter enter) {
        return new Response<>(inWhouseService.inWhouseSave(enter));
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value = "入库单新增", response = GeneralResult.class)
    public Response<GeneralResult> inWhouseEdit(@ModelAttribute @ApiParam("请求参数") InWhouseSaveOrUpdateEnter enter) {
        return new Response<>(inWhouseService.inWhouseEdit(enter));
    }


    @PostMapping(value = "/listCount")
    @ApiOperation(value = "列表统计", response = Map.class)
    public Response<Map<String,Integer>> listCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(inWhouseService.listCount(enter));
    }
}
