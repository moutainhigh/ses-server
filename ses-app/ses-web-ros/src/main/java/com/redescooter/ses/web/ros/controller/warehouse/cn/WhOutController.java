package com.redescooter.ses.web.ros.controller.warehouse.cn;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.wms.cn.WhOutService;
import com.redescooter.ses.web.ros.vo.wms.cn.StartWhOutOrderEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutSaveEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutWhResult;
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


@Log4j2
@Api(tags = {"CN-WH出库单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/warehouse/wms/cn/whOut")
public class WhOutController {

    @Autowired
    private WhOutService whOutService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = WhOutOrderListResult.class)
    public Response<PageResult<WhOutOrderListResult>> whOrderList(@ModelAttribute @ApiParam("请求参数") WhOutOrderListEnter enter) {
        return new Response<>(whOutService.whOrderList(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = WhOutDetailResult.class)
    public Response<WhOutDetailResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.detail(enter));
    }

    @PostMapping(value = "/nodeList")
    @ApiOperation(value = "订单节点", response = CommonNodeResult.class)
    public Response<List<CommonNodeResult>> nodeList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.nodeList(enter));
    }

    @PostMapping(value = "/detailProductPartList")
    @ApiOperation(value = "详情产品列表", response = WhOutDetailProductPartListResult.class)
    public Response<PageResult<WhOutDetailProductPartListResult>> detailProductPartList(@ModelAttribute @ApiParam("请求参数") WhOutDetailProductPartListEnter enter) {
        return new Response<>(whOutService.detailProductPartList(enter));
    }

    @PostMapping(value = "/cancel")
    @ApiOperation(value = "取消", response = GeneralResult.class)
    public Response<GeneralResult> cancel(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.cancel(enter));
    }

    @PostMapping(value = "/start")
    @ApiOperation(value = "开始调拨", response = GeneralResult.class)
    public Response<GeneralResult> start(@ModelAttribute @ApiParam("请求参数") StartWhOutOrderEnter enter) {
        return new Response<>(whOutService.start(enter));
    }

    @PostMapping(value = "/prepareMaterial")
    @ApiOperation(value = "确认备料", response = GeneralResult.class)
    public Response<GeneralResult> prepareMaterial(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.prepareMaterial(enter));
    }

    @PostMapping(value = "/outwh")
    @ApiOperation(value = "出库", response = GeneralResult.class)
    public Response<GeneralResult> outwh(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.outwh(enter));
    }

    @PostMapping(value = "/inWh")
    @ApiOperation(value = "入库", response = GeneralResult.class)
    public Response<GeneralResult> inWh(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.inWh(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") WhOutSaveEnter enter) {
        return new Response<>(whOutService.save(enter));
    }

    @PostMapping(value = "/consigneeList")
    @ApiOperation(value = "收货人列表", response = WhOutConsigneeResult.class)
    public Response<List<WhOutConsigneeResult>> consigneeList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(whOutService.consigneeList(enter));
    }

    @PostMapping(value = "/whList")
    @ApiOperation(value = "仓库列表", response = WhOutWhResult.class)
    public Response<List<WhOutWhResult>> whList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(whOutService.whList(enter));
    }

    @PostMapping(value = "/consignType")
    @ApiOperation(value = "物流方式", response = Map.class)
    public Response<Map<String, String>> consignType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(whOutService.consignType(enter));
    }

    @PostMapping(value = "/consignMethod")
    @ApiOperation(value = "委托方式", response = Map.class)
    public Response<Map<String, String>> consignMethod(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response<>(whOutService.consignMethod(enter));
    }

    @PostMapping(value = "/statusByCount")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> statusByCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(whOutService.statusByCount(enter));
    }

    @PostMapping(value = "/productList")
    @ApiOperation(value = "新增订单产品列表", response = WhOutProductListResult.class)
    public Response<PageResult<WhOutProductListResult>> productList(@ModelAttribute @ApiParam("请求参数") WhOutProductListEnter enter) {
        return new Response<>(whOutService.productList(enter));
    }

}
