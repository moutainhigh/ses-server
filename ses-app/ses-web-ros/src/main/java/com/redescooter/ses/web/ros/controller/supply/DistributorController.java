package com.redescooter.ses.web.ros.controller.supply;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.distributor.DistributorService;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorAddEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorCityAndCPSelectorEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorEnableOrDisableEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorListEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorNameEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorUpdateEnter;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorCityAndCPSelectorResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorDetailResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorListResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorSaleProductResult;
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
 * @Description 经销商Controller
 * @Author Chris
 * @Date 2020/12/16 15:57
 */
@Api(value = "经销商控制器", tags = "经销商控制器")
@CrossOrigin
@RestController
@RequestMapping("/supply/distributor")
public class DistributorController {

    @Autowired
    private DistributorService distributorService;

    /**
     * 门店列表
     */
    @ApiOperation(value = "门店列表", notes = "门店列表")
    @PostMapping("/list")
    public Response<PageResult<DistributorListResult>> getList(@ModelAttribute DistributorListEnter enter) {
        return distributorService.getList(enter);
    }

    /**
     * 城市下拉框,城市和邮政编码联动
     */
    @ApiOperation(value = "城市下拉框,城市和邮政编码联动", notes = "城市下拉框,城市和邮政编码联动")
    @PostMapping("/city/cp")
    public Response<DistributorCityAndCPSelectorResult> getCityAndCPSelector(@ModelAttribute DistributorCityAndCPSelectorEnter enter) {
        return distributorService.getCityAndCPSelector(enter);
    }

    /**
     * 启用/停用门店
     */
    @ApiOperation(value = "启用/停用门店", notes = "启用/停用门店")
    @PostMapping("/enable/disable")
    public Response<GeneralResult> enableOrDisable(@ModelAttribute DistributorEnableOrDisableEnter enter) {
        return distributorService.enableOrDisable(enter);
    }

    /**
     * 门店名称
     */
    @ApiOperation(value = "门店名称", notes = "门店名称")
    @PostMapping("/name")
    public Response<BooleanResult> getNameList(@ModelAttribute DistributorNameEnter enter) {
        return distributorService.getNameList(enter);
    }

    /**
     * 门店类型选择销售,可售卖产品的数据来源
     */
    @ApiOperation(value = "门店类型选择销售,可售卖产品的数据来源", notes = "门店类型选择销售,可售卖产品的数据来源")
    @PostMapping("/sale/product")
    public Response<List<DistributorSaleProductResult>> getSaleProduct(@ModelAttribute GeneralEnter enter) {
        return distributorService.getSaleProduct(enter);
    }

    /**
     * 新增门店
     */
    @ApiOperation(value = "新增门店", notes = "新增门店")
    @PostMapping("/add")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> add(@ModelAttribute DistributorAddEnter enter) {
        return distributorService.add(enter);
    }

    /**
     * 编辑门店
     */
    @ApiOperation(value = "编辑门店", notes = "编辑门店")
    @PostMapping("/update")
    public Response<GeneralResult> update(@ModelAttribute DistributorUpdateEnter enter) {
        return distributorService.update(enter);
    }

    /**
     * 删除门店
     */
    @ApiOperation(value = "删除门店", notes = "删除门店")
    @PostMapping("/delete")
    public Response<GeneralResult> delete(@ModelAttribute IdEnter enter) {
        return distributorService.delete(enter);
    }

    /**
     * 门店详情
     */
    @ApiOperation(value = "门店详情", notes = "门店详情")
    @PostMapping("/detail")
    public Response<DistributorDetailResult> getDetail(@ModelAttribute IdEnter enter) {
        return distributorService.getDetail(enter);
    }

}
