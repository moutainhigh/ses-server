package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.hub.service.website.SyncPriceService;
import com.redescooter.ses.api.hub.vo.website.SyncSalePriceDataEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.base.OpePartsMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSalePartsMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSalePriceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSetDepositMapper;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpeSaleParts;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSetDeposit;
import com.redescooter.ses.web.ros.enums.distributor.StatusEnum;
import com.redescooter.ses.web.ros.enums.salePrice.SalePriceEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.base.OpeSalePriceService;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterBatteryRelationService;
import com.redescooter.ses.web.ros.service.base.OpeSetDepositService;
import com.redescooter.ses.web.ros.service.restproduction.SalesPriceService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SetDepositEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesPriceResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.zookeeper.Op;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * @Description ????????????ServiceImpl
 * @Author Chris
 * @Date 2021/4/23 20:16
 */
@Service
@Slf4j
@DubboService
public class SalesPriceServiceImpl implements SalesPriceService {

    @Autowired
    private OpeSalePriceService opeSalePriceService;

    @Autowired
    private OpeSalePriceMapper opeSalePriceMapper;

    @Autowired
    private OpeSetDepositMapper opeSetDepositMapper;

    @Autowired
    private OpeSetDepositService opeSetDepositService;

    @Autowired
    private OpeSaleScooterBatteryRelationService opeSaleScooterBatteryRelationService;

    @Autowired
    private OpeSaleScooterMapper opeSaleScooterMapper;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private OpeSalePartsMapper opeSalePartsMapper;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private OpePartsMapper opePartsMapper;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private SyncPriceService syncPriceService;

    /**
     * ?????????????????????
     */
    @Override
    public List<String> getScooterBatteryList(GeneralEnter enter) {
        // ????????????????????????,??????en,??????fr
        String language = enter.getLanguage();
        LambdaQueryWrapper<OpeSaleScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSaleScooter::getDr, Constant.DR_FALSE);
        List<OpeSaleScooter> list = opeSaleScooterMapper.selectList(qw);
        List<String> list1 = new ArrayList<>();
        list.stream().forEach(item -> {
            String result = item.getProductCode() + "+" + item.getMinBatteryNum() + "Batterie";
            list1.add(result);
        });
        return list1;
    }

    /**
     * ??????
     */
    @Override
    public PageResult<OpeSalePrice> getSalePriceList(SalePriceListEnter enter) {
        int count = opeSalePriceMapper.getSalePriceCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OpeSalePrice> list = opeSalePriceMapper.getSalePriceList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult addSalePrice(SalePriceSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // ??????
        check(enter);

        OpeSalePrice price = new OpeSalePrice();
        price.setId(idAppService.getId(SequenceName.OPE_SALE_PRICE));
        price.setDr(Constant.DR_FALSE);
        price.setType(enter.getType());
        price.setScooterBattery(enter.getScooterBattery());
//        price.setDeposit(enter.getDeposit());
        price.setPeriod(enter.getPeriod());
        price.setShouldPayPeriod(enter.getShouldPayPeriod());
        price.setBalance(enter.getBalance());
        price.setTax(enter.getTax());
        price.setStatus(StatusEnum.DISABLE.getCode());
        price.setCreatedBy(enter.getUserId());
        price.setCreatedTime(new Date());
        boolean save = opeSalePriceService.save(price);
        //??????????????????????????????
        if (save) {
            LambdaQueryWrapper<OpeSetDeposit> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeSetDeposit::getDr,Constant.DR_FALSE);
            wrapper.last("limit 1");
            OpeSetDeposit setDeposit = opeSetDepositMapper.selectOne(wrapper);
            BigDecimal deposit = setDeposit.getDeposit();
            deposit = deposit == null ? new BigDecimal("0") : deposit;
            OpeSalePrice opeSalePrice = new OpeSalePrice();
            opeSalePrice.setDeposit(deposit);
            opeSalePrice.setId(price.getId());
            opeSalePriceMapper.updateById(opeSalePrice);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????
     */
    @Override
    public OpeSalePrice getSalePriceDetail(IdEnter enter) {
        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(price)) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        return price;
    }

    /**
     * ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePrice(SalePriceSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // ??????
        check(enter);

        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(price)) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        price.setScooterBattery(enter.getScooterBattery());
//        price.setDeposit(enter.getDeposit());
        price.setUpdatedBy(enter.getUserId());
        price.setUpdatedTime(new Date());
        price.setTax(enter.getTax());
        if (StringManaConstant.entityIsNotNull(enter.getPeriod())) {
            price.setPeriod(enter.getPeriod());
        }
        if (StringManaConstant.entityIsNotNull(enter.getShouldPayPeriod())) {
            price.setShouldPayPeriod(enter.getShouldPayPeriod());
        }
        if (StringManaConstant.entityIsNotNull(enter.getBalance())) {
            price.setBalance(enter.getBalance());
        }
        opeSalePriceService.updateById(price);
        return new GeneralResult(enter.getRequestId());
    }

    public void check(SalePriceSaveOrUpdateEnter enter) {
        if (StringUtils.isBlank(enter.getScooterBattery())) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_BATTERY_IS_EMPTY.getCode(), ExceptionCodeEnums.SCOOTER_BATTERY_IS_EMPTY.getMessage());
        }
        LambdaQueryWrapper<OpeSalePrice> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSalePrice::getDr, Constant.DR_FALSE);
        qw.eq(OpeSalePrice::getType, enter.getType());
        qw.eq(OpeSalePrice::getScooterBattery, enter.getScooterBattery());
        if (1 == enter.getType() || 3 == enter.getType()) {
            qw.eq(OpeSalePrice::getPeriod, enter.getPeriod());
        }
        if (StringManaConstant.entityIsNotNull(enter.getId())) {
            qw.ne(OpeSalePrice::getId, enter.getId());
        }
        int count = opeSalePriceService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.RULE_ALREADY_EXIST.getCode(), ExceptionCodeEnums.RULE_ALREADY_EXIST.getMessage());
        }
    }

    /**
     * ?????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSalePriceStatus(IdEnter enter) {
        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(price)) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        Integer status = price.getStatus();
        price.setStatus(status == 1 ? 2 : 1);
        opeSalePriceService.updateById(price);

        // ????????????
        syncSalePrice(price);
        return new GeneralResult(enter.getRequestId());
    }

    public void syncSalePrice(OpeSalePrice price) {
        if (2 == price.getStatus()) {
            // ??????????????????
            syncPriceService.syncDeleteSalePrice(price.getScooterBattery(), price.getType(), price.getPeriod());
        } else {
            // ??????????????????
            log.info("????????????????????????");
            SyncSalePriceDataEnter model = new SyncSalePriceDataEnter();
            model.setType(price.getType());
            model.setScooterBattery(price.getScooterBattery());
            model.setDeposit(price.getDeposit());
            model.setPeriod(price.getPeriod());
            model.setShouldPayPeriod(price.getShouldPayPeriod());
            model.setBalance(price.getBalance());
            model.setTax(price.getTax());
            syncPriceService.syncSalePrice(model);
            log.info("??????????????????");
        }
    }

    /**
     * ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteSalePrice(IdEnter enter) {
        OpeSalePrice price = opeSalePriceService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(price)) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.SALE_PRICE_IS_EMPTY.getMessage());
        }
        opeSalePriceService.removeById(enter.getId());

        // ????????????
        syncDeleteSalePrice(price);
        return new GeneralResult(enter.getRequestId());
    }

    public void syncDeleteSalePrice(OpeSalePrice price) {
        syncPriceService.syncDeleteSalePrice(price.getScooterBattery(), price.getType(), price.getPeriod());
    }

    /**
     * ??????tab???count
     */
    @Override
    public Map<String, Integer> getTabCount(GeneralEnter enter) {
        Map<String, Integer> result = Maps.newHashMapWithExpectedSize(3);
        LambdaQueryWrapper<OpeSalePrice> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSalePrice::getDr, Constant.DR_FALSE);
        List<OpeSalePrice> list = opeSalePriceService.list(qw);
        List<OpeSalePrice> borrowList = list.stream().filter(o -> o.getType() == 1).collect(Collectors.toList());
        List<OpeSalePrice> fullList = list.stream().filter(o -> o.getType() == 2).collect(Collectors.toList());
        List<OpeSalePrice> partList = list.stream().filter(o -> o.getType() == 3).collect(Collectors.toList());
        result.put("1", borrowList.size());
        result.put("2", fullList.size());
        result.put("3", partList.size());
        return result;
    }

//    @Override
//    public List<String> modelPriceList(GeneralEnter enter) {
//        List<String> lists = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        LambdaQueryWrapper<OpeSalePrice> qw = new LambdaQueryWrapper<>();
//        qw.eq(OpeSalePrice::getDr, Constant.DR_FALSE);
//        List<OpeSalePrice> list = opeSalePriceService.list(qw);
//        Map<String, List<OpeSalePrice>> ospMap = list.stream().collect(Collectors.groupingBy(OpeSalePrice::getScooterBattery));
//        LambdaQueryWrapper<OpeParts> qw2 = new LambdaQueryWrapper<>();
//        qw2.eq(OpeParts::getDr, Constant.DR_FALSE);
//        qw2.eq(OpeParts::getPartsType, 3);
//        qw2.last("limit 1");
//        OpeParts opeParts = opePartsMapper.selectOne(qw2);
//        if (opeParts == null) {
//            throw new SesWebRosException(ExceptionCodeEnums.NOT_FOUND_BATTERY.getCode(), ExceptionCodeEnums.NOT_FOUND_BATTERY.getMessage());
//        }
//        //?????????????????????????????????????????????partId?????????????????????????????????????????????
//        LambdaQueryWrapper<OpeSaleParts> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(OpeSaleParts::getPartsId, opeParts.getId());
//        OpeSaleParts opeSaleParts = opeSalePartsMapper.selectOne(queryWrapper);
//        if (opeSaleParts == null) {
//            throw new SesWebRosException(ExceptionCodeEnums.NOT_FOUND_PARTS.getCode(), ExceptionCodeEnums.NOT_FOUND_PARTS.getMessage());
//        }
//        //???????????????????????????
//        BigDecimal battery = opeSaleParts.getPrice();
//        Map<String, List<Integer>> numMap = Maps.newHashMap();
//        numMap.put(SalePriceEnum.showMsg(1), Lists.newArrayList(1, 2, 3, 4));
//        numMap.put(SalePriceEnum.showMsg(2), Lists.newArrayList(2, 3, 4));
//        numMap.put(SalePriceEnum.showMsg(3), Lists.newArrayList(4));
//        for (String ospKey : ospMap.keySet()) {
//            List<OpeSalePrice> eFiveList = ospMap.get(ospKey);
//            Map<Integer, OpeSalePrice> collect = eFiveList.stream().filter(o -> o.getType() != 2).collect(Collectors.toMap(OpeSalePrice::getPeriod, o -> o));
//            List<Integer> integers = numMap.get(ospKey);
//            //?????????????????????????????????
//            int minNum = integers.get(0);
//            for (int period : collect.keySet()) {
//                BigDecimal batteryPeriod = battery.divide(new BigDecimal(period), 2, BigDecimal.ROUND_DOWN);//?????????????????????
//                for (Integer o : integers) {
//                    Map<String, SalesPriceResult> map1 = new HashMap<>();
//                    BigDecimal shouldPayPeriod = collect.get(period).getShouldPayPeriod().add(batteryPeriod.multiply(new BigDecimal(o - minNum)));
//                    SalesPriceResult salesPriceResult = new SalesPriceResult();
//                    salesPriceResult.setShouldPayPeriod(shouldPayPeriod); //????????????
//                    salesPriceResult.setPeriod(period); //??????
//                    salesPriceResult.setDeposit(collect.get(period).getDeposit()); //??????
//                    salesPriceResult.setTax(collect.get(period).getTax()); //ttc??????
//                    map1.put(String.format("%s+%d", ospKey.substring(0, ospKey.indexOf("+")), o), salesPriceResult);
//                    String jsonObject = JSONObject.toJSONString(map1);  //map???json??????
//                    lists.add(jsonObject);
//                }
//            }
//        }
//        return lists;
//    }

    @Override
    public GeneralResult setDeposit(SetDepositEnter setDepositEnter) {
        if (setDepositEnter.getDeposit().compareTo(BigDecimal.ZERO) == 0) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPOSIT_NOT_ZERO.getCode(), ExceptionCodeEnums.DEPOSIT_NOT_ZERO.getMessage());
        }
        LambdaQueryWrapper<OpeSetDeposit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSetDeposit::getDr, Constant.DR_FALSE);
        wrapper.last("limit 1");
        OpeSetDeposit opeSetDeposit = opeSetDepositMapper.selectOne(wrapper);
        OpeSetDeposit opeSetDeposit1 = new OpeSetDeposit();
        opeSetDeposit1.setDeposit(setDepositEnter.getDeposit());
        opeSetDeposit1.setDr(Constant.DR_FALSE);
        int i = 0;
        if (null == opeSetDeposit) {
            i = opeSetDepositMapper.insert(opeSetDeposit1);
        } else {
            opeSetDeposit1.setId(opeSetDeposit.getId());
            i = opeSetDepositMapper.updateById(opeSetDeposit1);
        }
        if (i < 0) {
            throw new SesWebRosException(ExceptionCodeEnums.UPDATE_FAIL.getCode(), ExceptionCodeEnums.UPDATE_FAIL.getMessage());
        }
        LambdaQueryWrapper<OpeSalePrice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeSalePrice::getDr, Constant.DR_FALSE);
        List<OpeSalePrice> list = opeSalePriceMapper.selectList(queryWrapper);
        if (0 < list.size()) {
            OpeSetDeposit setDeposit = opeSetDepositMapper.selectOne(wrapper);
            setDepositEnter.setDeposit(setDeposit.getDeposit());
            opeSalePriceMapper.editDeposit(setDepositEnter);
            //????????????
            synchronizeDeposit(setDepositEnter);
        }

        return new GeneralResult(setDepositEnter.getRequestId());
    }

    @Override
    public BigDecimal TipSettings(GeneralEnter enter) {
        LambdaQueryWrapper<OpeSetDeposit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeSetDeposit::getDr, Constant.DR_FALSE);
        queryWrapper.isNotNull(OpeSetDeposit::getDeposit);
        queryWrapper.ne(OpeSetDeposit::getDeposit, 0);
        List<OpeSetDeposit> list = opeSetDepositMapper.selectList(queryWrapper);
        if (list.size() <= 0) {
            throw new SesWebRosException(ExceptionCodeEnums.NO_DEPOSIT_SET.getCode(), ExceptionCodeEnums.NO_DEPOSIT_SET.getMessage());
        }
        BigDecimal min = list
                .stream()
                .map(OpeSetDeposit::getDeposit)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        return min;
    }

    //???????????????????????????
    public void synchronizeDeposit(SetDepositEnter setDepositEnter) {
        SyncSalePriceDataEnter syncSalePriceDataEnter = new SyncSalePriceDataEnter();
        syncSalePriceDataEnter.setDeposit(setDepositEnter.getDeposit());
        syncPriceService.synchronizeDeposit(syncSalePriceDataEnter);
    }

}
