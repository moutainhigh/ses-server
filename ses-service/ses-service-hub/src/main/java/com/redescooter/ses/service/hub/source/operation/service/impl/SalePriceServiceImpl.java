package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.hub.service.operation.SalePriceService;
import com.redescooter.ses.service.hub.source.operation.dm.OpeSalePrice;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeSalePriceService;
import io.seata.common.util.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 19:49
 */
@DubboService
@DS("operation")
public class SalePriceServiceImpl implements SalePriceService {

    @Autowired
    private OpeSalePriceService opeSalePriceService;

    @DS("operation")
    @Override
    public void deleteSalePrice(String modelName) {
        LambdaQueryWrapper<OpeSalePrice> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSalePrice::getDr, Constant.DR_FALSE);
        qw.like(OpeSalePrice::getScooterBattery, modelName);
        List<OpeSalePrice> list = opeSalePriceService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> ids = list.stream().map(o -> o.getId()).collect(Collectors.toList());
            opeSalePriceService.removeByIds(ids);
        }
    }

}
