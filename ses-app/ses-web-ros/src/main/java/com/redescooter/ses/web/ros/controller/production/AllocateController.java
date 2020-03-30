package com.redescooter.ses.web.ros.controller.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.production.allocate.AllocateService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.ProductPartsListEnter;
import com.redescooter.ses.web.ros.vo.production.ProductPartsResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderNodeResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderPartResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderResult;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAllocateEnter;
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

import java.util.List;
import java.util.Map;

/**
 * @ClassName:AllocateController
 * @description: AllocateController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 16:53
 */
@Log4j2
@Api(tags = {"调拨模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/allocate")
public class AllocateController {

    @Autowired
    private AllocateService allocateService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "调拨单类型", response = Map.class)
    public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(allocateService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "调拨单状态列表", response = Map.class)
    public Response<Map<String, String>> statusList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(allocateService.statusList(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "调拨单列表", response = AllocateOrderResult.class)
    public Response<PageResult<AllocateOrderResult>> list(@ModelAttribute @ApiParam("请求参数") AllocateOrderEnter enter) {
        return new Response<>(allocateService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "调拨单详情", response = AllocateOrderResult.class)
    public Response<AllocateOrderResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateService.detail(enter));
    }

    @PostMapping(value = "/nodeList")
    @ApiOperation(value = "调拨单节点", response = AllocateOrderNodeResult.class)
    public Response<List<AllocateOrderNodeResult>> allocateOrderNode(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateService.allocateOrderNode(enter));
    }

    @PostMapping(value = "/detailPartsList")
    @ApiOperation(value = "调拨单详情部件列表", response = AllocateOrderPartResult.class)
    public Response<List<AllocateOrderPartResult>> allocateOrderDetailPartsList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateService.allocateOrderDetailPartsList(enter));
    }

    @PostMapping(value = "/startPrepare")
    @ApiOperation(value = "开始备料", response = GeneralResult.class)
    public Response<GeneralResult> startPrepare(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateService.startPrepare(enter));
    }

    @PostMapping(value = "/startAllocate")
    @ApiOperation(value = "开始调拨单", response = GeneralResult.class)
    public Response<GeneralResult> startAllocate(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateService.startAllocate(enter));
    }

    @PostMapping(value = "/cancel")
    @ApiOperation(value = "取消调拨单", response = GeneralResult.class)
    public Response<GeneralResult> cancelAllocate(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateService.cancelAllocate(enter));
    }

    @PostMapping(value = "/partsList")
    @ApiOperation(value = "新增调拨单部件列表", response = ProductPartsResult.class)
    public Response<List<ProductPartsResult>> allocatePartsList(@ModelAttribute @ApiParam("请求参数") ProductPartsListEnter enter) {
        return new Response<>(allocateService.allocatePartsList(enter));
    }

    @PostMapping(value = "/inWhAllocate")
    @ApiOperation(value = "调拨单入库", response = GeneralResult.class)
    public Response<GeneralResult> inWhAllocate(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateService.inWhAllocate(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存调拨单", response = GeneralResult.class)
    public Response<GeneralResult> saveAllocate(@ModelAttribute @ApiParam("请求参数") SaveAllocateEnter enter) {
        return new Response<>(allocateService.saveAllocate(enter));
    }

    @PostMapping(value = "/consigneeList")
    @ApiOperation(value = "调拨单收获人列表", response = ConsigneeResult.class)
    public Response<List<ConsigneeResult>> consigneeList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(allocateService.consigneeList(enter));
    }
}
