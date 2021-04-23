package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.service.base.OpeSalePriceService;
import com.redescooter.ses.web.ros.service.restproduction.SalesPriceService;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceListEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 销售价格ServiceImpl
 * @Author Chris
 * @Date 2021/4/23 20:16
 */
@Service
@Slf4j
public class SalesPriceServiceImpl implements SalesPriceService {

    @Autowired
    private OpeSalePriceService opeSalePriceService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 车型下拉数据源
     */
    @Override
    public List<String> getScooterBatteryList(GeneralEnter enter) {
        return null;
    }

    /**
     * 列表
     */
    @Override
    public PageResult<OpeSalePrice> getSalePriceList(SalePriceListEnter enter) {
        return null;
    }

    /**
     * 新建
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult addSalePrice(OpeSalePrice enter) {
        return null;
    }

    /**
     * 详情
     */
    @Override
    public OpeSalePrice getSalePriceDetail(IdEnter enter) {
        return null;
    }

    /**
     * 编辑
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePrice(OpeSalePrice enter) {
        return null;
    }

    /**
     * 开启或关闭状态
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePriceStatus(IdEnter enter) {
        return null;
    }

    /**
     * 删除
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteSalePrice(IdEnter enter) {
        return null;
    }

}
