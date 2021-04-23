package com.redescooter.ses.web.ros.controller.product;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.service.restproduction.SalesPriceService;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceListEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 销售价格控制器
 * @Author Chris
 * @Date 2021/4/23 20:13
 */
@Api(value = "销售价格控制器", tags = "销售价格控制器")
@CrossOrigin
@RestController
@RequestMapping("/sales/price")
public class SalesPriceController {

    @Autowired
    private SalesPriceService salesPriceService;

    /**
     * 车型下拉数据源
     */
    @PostMapping("/data")
    @ApiOperation(value = "车型下拉数据源", notes = "车型下拉数据源")
    public Response<List<String>> getScooterBatteryList(@ModelAttribute GeneralEnter enter) {
        return new Response<>(salesPriceService.getScooterBatteryList(enter));
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "列表", notes = "列表")
    public Response<PageResult<OpeSalePrice>> getSalePriceList(@ModelAttribute SalePriceListEnter enter) {
        return new Response<>(salesPriceService.getSalePriceList(enter));
    }

    /**
     * 新建
     */
    @PostMapping("/add")
    @ApiOperation(value = "新建", notes = "新建")
    public Response<GeneralResult> addSalePrice(@ModelAttribute OpeSalePrice enter) {
        return new Response<>(salesPriceService.addSalePrice(enter));
    }

    /**
     * 详情
     */
    @PostMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public Response<OpeSalePrice> getSalePriceDetail(@ModelAttribute IdEnter enter) {
        return new Response<>(salesPriceService.getSalePriceDetail(enter));
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑", notes = "编辑")
    public Response<GeneralResult> editSalePrice(@ModelAttribute OpeSalePrice enter) {
        return new Response<>(salesPriceService.editSalePrice(enter));
    }

    /**
     * 开启或关闭状态
     */
    @PostMapping("/status")
    @ApiOperation(value = "开启或关闭状态", notes = "开启或关闭状态")
    public Response<GeneralResult> editSalePriceStatus(@ModelAttribute IdEnter enter) {
        return new Response<>(salesPriceService.editSalePriceStatus(enter));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public Response<GeneralResult> deleteSalePrice(@ModelAttribute IdEnter enter) {
        return new Response<>(salesPriceService.deleteSalePrice(enter));
    }


}
