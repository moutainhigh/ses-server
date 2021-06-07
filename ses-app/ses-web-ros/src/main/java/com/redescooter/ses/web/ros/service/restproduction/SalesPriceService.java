package com.redescooter.ses.web.ros.service.restproduction;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SetDepositEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesPriceResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 销售价格Service
 * @Author Chris
 * @Date 2021/4/23 20:16
 */
public interface SalesPriceService {

    /**
     * 车型下拉数据源
     */
    List<String> getScooterBatteryList(GeneralEnter enter);

    /**
     * 列表
     */
    PageResult<OpeSalePrice> getSalePriceList(SalePriceListEnter enter);

    /**
     * 新建
     */
    GeneralResult addSalePrice(SalePriceSaveOrUpdateEnter enter);

    /**
     * 详情
     */
    OpeSalePrice getSalePriceDetail(IdEnter enter);

    /**
     * 编辑
     */
    GeneralResult editSalePrice(SalePriceSaveOrUpdateEnter enter);

    /**
     * 开启或关闭状态
     */
    GeneralResult editSalePriceStatus(IdEnter enter);

    /**
     * 删除
     */
    GeneralResult deleteSalePrice(IdEnter enter);

    /**
     * 每个tab的count
     */
    Map<String, Integer> getTabCount(GeneralEnter enter);


    List<String> modelPriceList(GeneralEnter enter);

    GeneralResult setDeposit(SetDepositEnter setDepositEnter);

    BigDecimal TipSettings(GeneralEnter enter);

}
