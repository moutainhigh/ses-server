package com.redescooter.ses.web.ros.controller.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.production.purchasingWh.PurchasingWhService;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListResult;
import com.redescooter.ses.web.ros.vo.production.wh.OutWhResult;
import com.redescooter.ses.web.ros.vo.production.wh.QcingListResult;
import com.redescooter.ses.web.ros.vo.production.wh.TobeStoredResult;
import com.redescooter.ses.web.ros.vo.production.wh.WasteResult;
import com.redescooter.ses.web.ros.vo.production.wh.WhEnter;
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

import java.util.Map;

/**
 * @ClassName:PurchasingWhController
 * @description: PurchasingWhController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 18:10
 */
@Log4j2
@Api(tags = {"采购仓库模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/wh")
public class PurchasingWhController {
    @Autowired
    private PurchasingWhService purchasingWhService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "仓库类型统计", response = Map.class)
    public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingWhService.countByType(enter));
    }

    @PostMapping(value = "/productTypeList")
    @ApiOperation(value = "仓库产品类型", response = Map.class)
    public Response<Map<String, String>> productTypeList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingWhService.productTypeList(enter));
    }

    @PostMapping(value = "/availableList")
    @ApiOperation(value = "可用列表", response = AvailableListResult.class)
    public Response<PageResult<AvailableListResult>> availableList(@ModelAttribute @ApiParam("请求参数") WhEnter enter) {
        return new Response<>(purchasingWhService.availableList(enter));
    }

    @PostMapping(value = "/qcingList")
    @ApiOperation(value = "质检中列表", response = QcingListResult.class)
    public Response<PageResult<QcingListResult>> qcingList(@ModelAttribute @ApiParam("请求参数") WhEnter enter) {
        return new Response<>(purchasingWhService.qcingList(enter));
    }

    @PostMapping(value = "/tobeStoredList")
    @ApiOperation(value = "入库列表", response = TobeStoredResult.class)
    public Response<PageResult<TobeStoredResult>> tobeStoredList(@ModelAttribute @ApiParam("请求参数") WhEnter enter) {
        return new Response<>(purchasingWhService.tobeStoredList(enter));
    }

    @PostMapping(value = "/outWhList")
    @ApiOperation(value = "出库列表", response = OutWhResult.class)
    public Response<PageResult<OutWhResult>> outWhList(@ModelAttribute @ApiParam("请求参数") WhEnter enter) {
        return new Response<>(purchasingWhService.outWhList(enter));
    }

    @PostMapping(value = "/wasteList")
    @ApiOperation(value = "废料列表", response = WasteResult.class)
    public Response<PageResult<WasteResult>> wasteList(@ModelAttribute @ApiParam("请求参数") WhEnter enter) {
        return new Response<>(purchasingWhService.wasteList(enter));
    }

    // @PostMapping(value = "/canAssemblyProductList")
    // @ApiOperation(value = "能够组装的商品", response = Map.class)
    // public Response< Map<String, Integer>> canAssemblyProductList(@ModelAttribute @ApiParam("请求参数") GeneralEnter
    // enter) {
    // return new Response<>(purchasingWhService.canAssemblyProductList(enter));
    // }

}
