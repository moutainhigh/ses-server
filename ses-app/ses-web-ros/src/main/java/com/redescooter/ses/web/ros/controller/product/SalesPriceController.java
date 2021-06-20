package com.redescooter.ses.web.ros.controller.product;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.service.restproduction.SalesPriceService;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SetDepositEnter;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    @AvoidDuplicateSubmit
    public Response<GeneralResult> addSalePrice(@ModelAttribute SalePriceSaveOrUpdateEnter enter) {
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
    @AvoidDuplicateSubmit
    public Response<GeneralResult> editSalePrice(@ModelAttribute SalePriceSaveOrUpdateEnter enter) {
        return new Response<>(salesPriceService.editSalePrice(enter));
    }

    /**
     * 开启或关闭状态
     */
    @PostMapping("/status")
    @ApiOperation(value = "开启或关闭状态", notes = "开启或关闭状态")
    @AvoidDuplicateSubmit
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

    /**
     * 每个tab的count
     */
    @PostMapping("/count")
    @ApiOperation(value = "每个tab的count", notes = "每个tab的count")
    public Response<Map<String, Integer>> getTabCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(salesPriceService.getTabCount(enter));
    }



    /**
     * 单独统一设置定金
     *
     * @param setDepositEnter
     * @return
     */
    @GlobalTransactional
    @PostMapping("/setDeposit")
    @ApiOperation(value = "单独设置价格", notes = "单独设置价格")
    public Response<Map<String, Integer>> setDeposit(@ModelAttribute SetDepositEnter setDepositEnter) {
        return new Response(salesPriceService.setDeposit(setDepositEnter));
    }

    /**
     *提示设置定金
     */
    @PostMapping("/tipSettingsDeposit")
    @ApiOperation(value = "提示设置定金", notes = "提示设置定金")
    public Response<Map<String, Integer>> TipSettingsDeposit(@ModelAttribute GeneralEnter enter) {
        return new Response(salesPriceService.TipSettings(enter));
    }
}
