package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSalePriceMapper;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.dm.OpeSaleScooterBatteryRelation;
import com.redescooter.ses.web.ros.enums.distributor.StatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSalePriceService;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterBatteryRelationService;
import com.redescooter.ses.web.ros.service.restproduction.SalesPriceService;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceSaveOrUpdateEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private OpeSalePriceMapper opeSalePriceMapper;

    @Autowired
    private OpeSaleScooterBatteryRelationService opeSaleScooterBatteryRelationService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 车型下拉数据源
     */
    @Override
    public List<String> getScooterBatteryList(GeneralEnter enter) {
        // 获取请求头的语言,英文en,法文fr
        String language = enter.getLanguage();
        LambdaQueryWrapper<OpeSaleScooterBatteryRelation> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSaleScooterBatteryRelation::getDr, Constant.DR_FALSE);
        qw.eq(OpeSaleScooterBatteryRelation::getLanguage, language);
        List<OpeSaleScooterBatteryRelation> list = opeSaleScooterBatteryRelationService.list(qw);
        List<String> result = list.stream().sorted(Comparator.comparing(OpeSaleScooterBatteryRelation::getCreatedTime)).map(o -> o.getContent()).collect(Collectors.toList());
        return result;
    }

    /**
     * 列表
     */
    @Override
    public PageResult<OpeSalePrice> getSalePriceList(SalePriceListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int count = opeSalePriceMapper.getSalePriceCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OpeSalePrice> list = opeSalePriceMapper.getSalePriceList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 新建
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult addSalePrice(SalePriceSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // 校验


        OpeSalePrice price = new OpeSalePrice();
        price.setId(idAppService.getId(SequenceName.OPE_SALE_PRICE));
        price.setDr(Constant.DR_FALSE);
        price.setType(enter.getType());
        price.setScooterBattery(enter.getScooterBattery());
        price.setDeposit(enter.getDeposit());
        price.setPeriod(enter.getPeriod());
        price.setShouldPayPeriod(enter.getShouldPayPeriod());
        price.setBalance(enter.getBalance());
        price.setStatus(StatusEnum.DISABLE.getCode());
        price.setCreatedBy(enter.getUserId());
        price.setCreatedTime(new Date());
        opeSalePriceService.save(price);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 详情
     */
    @Override
    public OpeSalePrice getSalePriceDetail(IdEnter enter) {
        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (null == price) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        return price;
    }

    /**
     * 编辑
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePrice(SalePriceSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // 校验


        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (null == price) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        price.setScooterBattery(enter.getScooterBattery());
        price.setDeposit(enter.getDeposit());
        price.setUpdatedBy(enter.getUserId());
        price.setUpdatedTime(new Date());
        if (null != enter.getPeriod()) {
            price.setPeriod(enter.getPeriod());
        }
        if (null != enter.getShouldPayPeriod()) {
            price.setShouldPayPeriod(enter.getShouldPayPeriod());
        }
        if (null != enter.getBalance()) {
            price.setBalance(enter.getBalance());
        }
        opeSalePriceService.updateById(price);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开启或关闭状态
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePriceStatus(IdEnter enter) {
        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (null == price) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        Integer status = price.getStatus();
        price.setStatus(status == 1 ? 2 : 1);
        opeSalePriceService.updateById(price);

        // 数据同步


        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteSalePrice(IdEnter enter) {
        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (null == price) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        opeSalePriceService.removeById(enter.getId());

        // 数据同步


        return new GeneralResult(enter.getRequestId());
    }

}
