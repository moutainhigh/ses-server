package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.operation.SalePriceService;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.exception.salePrice.SalePriceEnum;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpePartsMapper;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeSalePartsMapper;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeParts;
import com.redescooter.ses.service.hub.source.operation.dm.OpeSaleParts;
import com.redescooter.ses.service.hub.source.operation.dm.OpeSalePrice;
import com.redescooter.ses.service.hub.source.operation.dm.OpeSaleScooter;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeSalePriceService;
import com.redescooter.ses.service.hub.source.operation.vo.SalesPriceResult;
import io.seata.common.util.CollectionUtils;
import io.swagger.models.auth.In;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private OpePartsMapper opePartsMapper;

    @Autowired
    private OpeSalePartsMapper opeSalePartsMapper;

    @Autowired
    private OpeSaleScooterMapper opeSaleScooterMapper;

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

    @Override
    @DS("operation")
    public List<String> modelPriceList(GeneralEnter enter) {
        List<String> lists = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        LambdaQueryWrapper<OpeSalePrice> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSalePrice::getDr, Constant.DR_FALSE);
        List<OpeSalePrice> list = opeSalePriceService.list(qw);
        Map<String, List<OpeSalePrice>> ospMap = list.stream().collect(Collectors.groupingBy(OpeSalePrice::getScooterBattery));
        LambdaQueryWrapper<OpeParts> qw2 = new LambdaQueryWrapper<>();
        qw2.eq(OpeParts::getDr, Constant.DR_FALSE);
        qw2.eq(OpeParts::getPartsType, 3);
        qw2.last("limit 1");
        OpeParts opeParts = opePartsMapper.selectOne(qw2);
        if (opeParts == null) {
            throw new SeSHubException(ExceptionCodeEnums.NOT_FOUND_BATTERY.getCode(), ExceptionCodeEnums.NOT_FOUND_BATTERY.getMessage());
        }
        //根据在部件表里面查出来的电池的partId去配件销售表里面查找对应的价格
        LambdaQueryWrapper<OpeSaleParts> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeSaleParts::getPartsId, opeParts.getId());
        OpeSaleParts opeSaleParts = opeSalePartsMapper.selectOne(queryWrapper);
        if (opeSaleParts == null) {
            throw new SeSHubException(ExceptionCodeEnums.NOT_FOUND_PARTS.getCode(), ExceptionCodeEnums.NOT_FOUND_PARTS.getMessage());
        }
        //拿到单个电池的价格
        BigDecimal battery = opeSaleParts.getPrice();
        LambdaQueryWrapper<OpeSaleScooter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSaleScooter::getDr, Constant.DR_FALSE);
        List<OpeSaleScooter> saleScooterList = opeSaleScooterMapper.selectList(wrapper);
        Map<String, Integer> numMap = saleScooterList.stream().collect(Collectors.toMap(OpeSaleScooter::getProductCode, o -> o.getMinBatteryNum()));
        for (String ospKey : ospMap.keySet()) {
            List<OpeSalePrice> eFiveList = ospMap.get(ospKey);
            Map<Integer, OpeSalePrice> collect = new HashMap<>();
            List<OpeSalePrice> collects = eFiveList.stream().filter(o -> o.getType() == 1 || o.getType() == 3).collect(Collectors.toList());
            if (collects.size() > 1) {
                collect = eFiveList.stream().filter(o -> o.getType() == 3).collect(Collectors.toMap(OpeSalePrice::getPeriod, o -> o));
            } else {
                collect = eFiveList.stream().filter(o -> o.getType() == 1 || o.getType() == 3).collect(Collectors.toMap(OpeSalePrice::getPeriod, o -> o));
            }
            //获取最低配置电池数量值
            String configKey = ospKey.substring(0, ospKey.indexOf("+"));
            int minNum = numMap.get(configKey);
            int i = minNum;
            for (int period : collect.keySet()) {
                BigDecimal batteryPeriod = battery.divide(new BigDecimal(period), 2, BigDecimal.ROUND_DOWN);//电池分期的价格
                for (i = minNum; i <= 4; i++) {
                    Map<String, SalesPriceResult> map1 = new HashMap<>();
                    BigDecimal shouldPayPeriod = collect.get(period).getShouldPayPeriod().add(batteryPeriod.multiply(new BigDecimal(i - minNum)));
                    SalesPriceResult salesPriceResult = new SalesPriceResult();
                    salesPriceResult.setShouldPayPeriod(shouldPayPeriod); //每期应付
                    salesPriceResult.setPeriod(period); //期数
                    salesPriceResult.setDeposit(collect.get(period).getDeposit()); //定金
                    salesPriceResult.setTax(collect.get(period).getTax()); //ttc税金
                    map1.put(String.format("%s+%d", configKey, i), salesPriceResult);
                    String jsonObject = JSONObject.toJSONString(map1);  //map转json格式
                    lists.add(jsonObject);
                }
            }
        }
        return lists;
    }
}
