package com.redescooter.ses.service.mobile.b.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.StatisticalDataService;
import com.redescooter.ses.api.mobile.b.vo.AllMobileBScooterChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBDeliveryChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBScooterChartResult;
import com.redescooter.ses.api.mobile.b.vo.MonthlyDeliveryChartResult;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryStatEnter;
import com.redescooter.ses.service.mobile.b.constant.SequenceName;
import com.redescooter.ses.service.mobile.b.dao.DeliveryServiceMapper;
import com.redescooter.ses.service.mobile.b.dao.StatisticalDataServiceMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverRideStatDetailMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverRideStatMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverScooterMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorScooterRideStatDetailMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorScooterRideStatMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStat;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStatDetail;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStat;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStatDetail;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName:StatisticalDataServiceImpl
 * @description: StatisticalDataServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 17:01
 */
@DubboService
public class StatisticalDataServiceImpl implements StatisticalDataService {

    @Autowired
    private CorDriverMapper corDriverMapper;

    @Autowired
    private CorDriverRideStatMapper corDriverRideStatMapper;

    @Autowired
    private CorDriverScooterMapper corDriverScooterMapper;

    @Autowired
    private CorDriverRideStatDetailMapper corDriverRideStatDetailMapper;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private CorScooterRideStatMapper corScooterRideStatMapper;

    @Autowired
    private CorScooterRideStatDetailMapper corScooterRideStatDetailMapper;

    @Autowired
    private DeliveryServiceMapper deliveryServiceMapper;

    @Autowired
    private StatisticalDataServiceMapper statisticalDataServiceMapper;

    /**
     * 保存司机骑行数据
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void saveDriverRideStat(List<SaveDeliveryStatEnter> enter) {

        List<CorDriverRideStatDetail> saveDriverRideStatDetailList = new ArrayList<>();

        List<CorDriverRideStat> saveDriverRideStatList = new ArrayList<>();

        List<CorDriverRideStat> updateDriverRideStatList = new ArrayList<>();

        // 查询司机之前是否存在有统计数据
        if (CollectionUtils.isNotEmpty(enter)) {
            for (SaveDeliveryStatEnter item : enter) {
                QueryWrapper<CorDriver> queryDriverWrapper = new QueryWrapper<>();
                queryDriverWrapper.eq(CorDriver.COL_USER_ID, item.getInputUserId());
                queryDriverWrapper.eq(CorDriver.COL_DR, 0);
                CorDriver driver = corDriverMapper.selectOne(queryDriverWrapper);

                QueryWrapper<CorDriverRideStat> driverRideStatQueryWrapper = new QueryWrapper<>();
                driverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DRIVER_ID, driver.getId());
                driverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DR, 0);
                CorDriverRideStat driverRideStat = corDriverRideStatMapper.selectOne(driverRideStatQueryWrapper);


                Boolean checkSaveDriverRideStatList = Boolean.FALSE;
                if (CollectionUtils.isNotEmpty(saveDriverRideStatList)) {
                    for (CorDriverRideStat corDriverRideStat : saveDriverRideStatList) {
                        if (corDriverRideStat.getDriverId().equals(driver.getId())) {
                            driverRideStat = corDriverRideStat;
                            checkSaveDriverRideStatList = Boolean.TRUE;
                            saveDriverRideStatList.remove(corDriverRideStat);
                            break;
                        }
                    }
                }

                // 插入详情
                CorDriverRideStatDetail driverRideStatDetail = buildCorDriverRideStatDetailSingle(item, driverRideStat, driver.getId());
                saveDriverRideStatDetailList.add(driverRideStatDetail);

                if (driverRideStat == null) {
                    saveDriverRideStatList.add(buildCorDriverRideStat(item, driver, driverRideStatDetail));
                } else {
                    driverRideStat.setCo2Total(driverRideStat.getCo2Total().add(driverRideStatDetail.getCo2Increment()));
                    driverRideStat.setCo2Increment(driverRideStatDetail.getCo2Increment());
                    driverRideStat.setTotalDuration(driverRideStat.getTotalDuration() + item.getDuration());
                    driverRideStat.setSavedMoney(driverRideStat.getSavedMoney().add(driverRideStatDetail.getSavedMoney()));
                    driverRideStat.setSvgSpeed(driverRideStat.getSvgSpeed().add(driverRideStatDetail.getSvgSpeed()));
                    driverRideStat.setTotalMileage(driverRideStat.getTotalMileage().add(driverRideStatDetail.getMileage()));
                    driverRideStat.setUpdateBy(item.getInputUserId());
                    driverRideStat.setUpdateTime(new Date());
                    if (checkSaveDriverRideStatList) {
                        saveDriverRideStatList.add(driverRideStat);
                    } else {
                        updateDriverRideStatList.add(driverRideStat);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(saveDriverRideStatDetailList)) {
            corDriverRideStatDetailMapper.batchInsert(saveDriverRideStatDetailList);
        }
        if (CollectionUtils.isNotEmpty(saveDriverRideStatList)) {
            corDriverRideStatMapper.batchInsert(saveDriverRideStatList);
        }
        if (CollectionUtils.isNotEmpty(updateDriverRideStatList)) {
            corDriverRideStatMapper.batchInsert(updateDriverRideStatList);
        }

    }

    /**
     * 车辆统计数据
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void saveScooterRideStat(List<SaveDeliveryStatEnter> enter) {
        List<CorScooterRideStatDetail> saveCorScooterRideStatDetailList = new ArrayList<>();

        List<CorScooterRideStat> saveCorScooterRideStatList = new ArrayList<>();

        List<CorScooterRideStat> updateCorScooterRideStatList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(enter)) {
            for (SaveDeliveryStatEnter item : enter) {// 查询司机之前是否存在有统计数据
                QueryWrapper<CorDriver> corDriverMapperQueryWrapper = new QueryWrapper<>();
                corDriverMapperQueryWrapper.eq(CorDriver.COL_USER_ID, item.getInputUserId());
                corDriverMapperQueryWrapper.eq(CorDriver.COL_DR, 0);
                CorDriver corDriver = corDriverMapper.selectOne(corDriverMapperQueryWrapper);


                QueryWrapper<CorDriverScooter> driverScooterQueryWrapper = new QueryWrapper<>();
                driverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, corDriver.getId());
                driverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
                driverScooterQueryWrapper.in(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
                CorDriverScooter driverScooter = corDriverScooterMapper.selectOne(driverScooterQueryWrapper);

                QueryWrapper<CorScooterRideStat> scooterRideStatQueryWrapper = new QueryWrapper<>();
                scooterRideStatQueryWrapper.eq(CorScooterRideStat.COL_DR, 0);
                scooterRideStatQueryWrapper.eq(CorScooterRideStat.COL_SCOOTER_ID, driverScooter.getScooterId());
                CorScooterRideStat scooterRideStat = corScooterRideStatMapper.selectOne(scooterRideStatQueryWrapper);

                Boolean checkSaveScooterRideStatList = Boolean.FALSE;
                if (CollectionUtils.isNotEmpty(saveCorScooterRideStatList)) {
                    for (CorScooterRideStat corScooterRideStat : saveCorScooterRideStatList) {
                        if (corScooterRideStat.getScooterId().equals(driverScooter.getScooterId())) {
                            scooterRideStat = corScooterRideStat;
                            checkSaveScooterRideStatList = Boolean.TRUE;
                            saveCorScooterRideStatList.remove(corScooterRideStat);
                            break;
                        }
                    }
                }

                // 插入详情
                CorScooterRideStatDetail scooterRideStatDetail = buildScooterRideStatDetail(item, driverScooter, scooterRideStat);
                saveCorScooterRideStatDetailList.add(scooterRideStatDetail);

                if (scooterRideStat == null) {
                    saveCorScooterRideStatList.add(buildScooterRideStat(item, driverScooter, scooterRideStatDetail));
                } else {
                    scooterRideStat.setTotalDuration(scooterRideStat.getTotalDuration() + item.getDuration());
                    scooterRideStat.setCo2Total(scooterRideStat.getCo2Total().add(scooterRideStatDetail.getCo2Increment()));
                    scooterRideStat.setCo2Increment(scooterRideStatDetail.getCo2Increment());
                    scooterRideStat.setSavedMoney(scooterRideStat.getSavedMoney().add(scooterRideStatDetail.getSavedMoney()));
                    scooterRideStat.setSvgSpeed(scooterRideStat.getSvgSpeed().add(scooterRideStatDetail.getSvgSpeed()));
                    scooterRideStat.setTotalMileage(scooterRideStat.getTotalMileage().add(new BigDecimal(item.getMileage())));
                    if (checkSaveScooterRideStatList) {
                        saveCorScooterRideStatList.add(scooterRideStat);
                    } else {
                        updateCorScooterRideStatList.add(scooterRideStat);
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(saveCorScooterRideStatDetailList)) {
            corScooterRideStatDetailMapper.batchInsert(saveCorScooterRideStatDetailList);
        }
        if (CollectionUtils.isNotEmpty(saveCorScooterRideStatList)) {
            corScooterRideStatMapper.batchInsert(saveCorScooterRideStatList);
        }
        if (CollectionUtils.isNotEmpty(updateCorScooterRideStatList)) {
            corScooterRideStatMapper.updateBatch(updateCorScooterRideStatList);
        }
    }

    /**
     * 订单数据图表统计
     *
     * @param enter
     * @return
     */
    @Override
    public MobileBDeliveryChartResult mobileBDeliveryChart(DateTimeParmEnter enter) {

        Map<String, MonthlyDeliveryChartResult> allMap = new LinkedHashMap<>();
        Map<String, MonthlyDeliveryChartResult> listMap = new LinkedHashMap<>();
        if (Objects.isNull(enter.getStartDateTime()) || Objects.isNull(enter.getEndDateTime())) {
            throw new MobileBException(ExceptionCodeEnums.DATE_IS_EMPTY.getCode(), ExceptionCodeEnums.DATE_IS_EMPTY.getMessage());
        }

        List<String> dayList = new LinkedList();
        // 获取指定日期格式向前N天时间集合
//            dayList = DateUtil.getDayList(enter.getDateTime() == null ? new Date() : enter.getDateTime(), 30, null);
        dayList = DateUtil.getBetweenDates(enter.getStartDateTime(), enter.getEndDateTime());
        List<MonthlyDeliveryChartResult> list = deliveryServiceMapper.mobileBDeliveryChart(enter);

        MonthlyDeliveryChartResult result = null;
        if (CollectionUtils.isEmpty(list)) {
            for (String str : dayList) {
                result = new MonthlyDeliveryChartResult();
                result.setTimes(str);
                allMap.put(str, result);
            }
        } else {
            // 获取指定日期格式向前N天时间集合
//            dayList = DateUtil.getDayList(enter.getDateTime() == null ? new Date() : enter.getDateTime(), 30, null);
            for (String str : dayList) {
                for (MonthlyDeliveryChartResult chart : list) {
                    if (chart.getTimes().equals(str)) {
                        allMap.put(str, chart);
                        listMap.put(str, chart);
                    }
                }
                if (!allMap.containsKey(str)) {
                    result = new MonthlyDeliveryChartResult();
                    result.setTimes(str);
                    allMap.put(str, result);
                }
            }
        }

        MobileBDeliveryChartResult chartResult = new MobileBDeliveryChartResult();
        chartResult.setAllMap(allMap);
        chartResult.setListMap(listMap);
        chartResult.setRequestId(enter.getRequestId());
        return chartResult;
    }

    /**
     * 车辆图表数据统计
     *
     * @param enter
     * @return
     */
    @Override
    public AllMobileBScooterChartResult mobileBScooterChart(DateTimeParmEnter enter) {

        Map<String, MobileBScooterChartResult> map = new LinkedHashMap<>();
        // 查询司机Id
        QueryWrapper<CorDriver> corDriverQueryWrapper = new QueryWrapper<>();
        corDriverQueryWrapper.eq(CorDriver.COL_USER_ID, enter.getUserId());
        corDriverQueryWrapper.eq(CorDriver.COL_DR, 0);
        corDriverQueryWrapper.last("LIMIT 1");
        CorDriver corDriver = corDriverMapper.selectOne(corDriverQueryWrapper);

        QueryWrapper<CorDriverRideStat> corDriverRideStatQueryWrapper = new QueryWrapper<>();
        corDriverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DR, 0);
        corDriverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DRIVER_ID, corDriver.getId());
        corDriverRideStatQueryWrapper.last("LIMIT 1");
        CorDriverRideStat corDriverRideStat = corDriverRideStatMapper.selectOne(corDriverRideStatQueryWrapper);
// 获取指定日期格式向前N天时间集合
        List<String> dayList = new LinkedList();
//        dayList = DateUtil.getDayList(enter.getDateTime() == null ? new Date() : enter.getDateTime(), 30, null);
        dayList = DateUtil.getBetweenDates(enter.getStartDateTime(), enter.getEndDateTime());
        MobileBScooterChartResult result = null;
        if (corDriverRideStat == null) {
            for (String str : dayList) {
                result = new MobileBScooterChartResult();
                result.setTimes(str);
                map.put(str, result);
            }
            return new AllMobileBScooterChartResult(map);
        }

        enter.setUserId(corDriver.getId());
        List<MobileBScooterChartResult> list = statisticalDataServiceMapper.mobileBScooterChart(enter);
        if (CollectionUtils.isNotEmpty(list)) {

            for (String str : dayList) {
                for (MobileBScooterChartResult chart : list) {
                    if (chart.getTimes().equals(str)) {
                        map.put(str, chart);
                    }
                }
                if (!map.containsKey(str)) {
                    result = new MobileBScooterChartResult();
                    result.setTimes(str);
                    map.put(str, result);
                }
            }
        } else {
            for (String str : dayList) {
                result = new MobileBScooterChartResult();
                result.setTimes(str);
                map.put(str, result);
            }
        }
        return new AllMobileBScooterChartResult(map);
    }

    @Override
    public Map<String, Integer> allDriverDeliveryStatusCount(GeneralEnter enter) {

        List<CountByStatusResult> countByStatusResults = deliveryServiceMapper.allDriverDeliveryStatusCount(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResults) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (DeliveryStatusEnums status : DeliveryStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        map.remove("1");
        map.remove("2");
        return map;
    }

    /**
     * 司机骑行总统计
     *
     * @param enter
     * @return
     */
    @Override
    public MobileBScooterChartResult mobileBAllScooterChart(GeneralEnter enter) {
        MobileBScooterChartResult result = new MobileBScooterChartResult();
        // 查询司机Id
        QueryWrapper<CorDriver> corDriverQueryWrapper = new QueryWrapper<>();
        corDriverQueryWrapper.eq(CorDriver.COL_USER_ID, enter.getUserId());
        corDriverQueryWrapper.eq(CorDriver.COL_TENANT_ID, enter.getTenantId());
        corDriverQueryWrapper.eq(CorDriver.COL_DR, 0);
        corDriverQueryWrapper.last("LIMIT 1");
        CorDriver corDriver = corDriverMapper.selectOne(corDriverQueryWrapper);

        QueryWrapper<CorDriverRideStat> corDriverRideStatQueryWrapper = new QueryWrapper<>();
        corDriverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DR, 0);
        corDriverRideStatQueryWrapper.eq(CorDriverRideStat.COL_TENANT_ID, enter.getTenantId());
        corDriverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DRIVER_ID, corDriver.getId());
        corDriverRideStatQueryWrapper.last("LIMIT 1");
        CorDriverRideStat corDriverRideStat = corDriverRideStatMapper.selectOne(corDriverRideStatQueryWrapper);
        if (corDriverRideStat == null) {
            return new MobileBScooterChartResult();
        }
        result.setAvgSpeed(String.valueOf(corDriverRideStat.getSvgSpeed()));
        result.setTotalMileage(String.valueOf(corDriverRideStat.getTotalMileage()));
        result.setTotalCo2(String.valueOf(corDriverRideStat.getCo2Total()));
        result.setTotalMoney(String.valueOf(corDriverRideStat.getSavedMoney()));
        return result;
    }

    private CorScooterRideStat buildScooterRideStat(SaveDeliveryStatEnter enter, CorDriverScooter
            driverScooter, CorScooterRideStatDetail scooterRideStatDetail) {
        CorScooterRideStat insertScooterRideStat = new CorScooterRideStat();
        insertScooterRideStat.setId(idAppService.getId(SequenceName.COR_SCOOTER_RIDE_STAT));
        insertScooterRideStat.setDr(0);
        insertScooterRideStat.setTenantId(enter.getInputTenantId());
        insertScooterRideStat.setScooterId(driverScooter.getScooterId());
        insertScooterRideStat.setTotalDuration(scooterRideStatDetail.getDuration());
        insertScooterRideStat.setCo2Total(scooterRideStatDetail.getCo2Increment());
        insertScooterRideStat.setCo2Increment(scooterRideStatDetail.getCo2Increment());
        insertScooterRideStat.setSavedMoney(scooterRideStatDetail.getSavedMoney());
        insertScooterRideStat.setSvgSpeed(scooterRideStatDetail.getSvgSpeed());
        insertScooterRideStat.setTotalMileage(new BigDecimal(enter.getMileage()));
        insertScooterRideStat.setFirstRideTime(enter.getLastUpdateTime());
        insertScooterRideStat.setCreateTime(enter.getLastUpdateTime());
        insertScooterRideStat.setCreateBy(enter.getInputUserId());
        insertScooterRideStat.setUpdateBy(enter.getInputUserId());
        insertScooterRideStat.setUpdateTime(enter.getLastUpdateTime());
        return insertScooterRideStat;
    }

    private CorScooterRideStatDetail buildScooterRideStatDetail(SaveDeliveryStatEnter enter, CorDriverScooter
            driverScooter, CorScooterRideStat scooterRideStat) {
        CorScooterRideStatDetail scooterRideStatDetail = new CorScooterRideStatDetail();
        scooterRideStatDetail.setId(idAppService.getId(SequenceName.COR_SCOOTER_RIDE_STAT_DETAIL));
        scooterRideStatDetail.setDr(0);
        scooterRideStatDetail.setTenantId(enter.getInputTenantId());
        scooterRideStatDetail.setBizId(enter.getBizId());
        scooterRideStatDetail.setBizType(enter.getBizType());
        scooterRideStatDetail.setScooterId(driverScooter.getScooterId());
        scooterRideStatDetail.setDuration(enter.getDuration());
        scooterRideStatDetail.setCo2HistoryTotal(enter.getCo2());
        scooterRideStatDetail.setCo2Increment(enter.getCo2());
        String avg = Double.toString(enter.getMileage() / (enter.getDuration() > 0 ? enter.getDuration() : 1));
        scooterRideStatDetail.setSvgSpeed(new BigDecimal(avg));
        scooterRideStatDetail.setMileage(new BigDecimal(enter.getMileage()));
        scooterRideStatDetail.setSavedMoney(enter.getMoney());
        scooterRideStatDetail.setCreateTime(enter.getLastUpdateTime());
        scooterRideStatDetail.setCreateBy(enter.getInputUserId());
        scooterRideStatDetail.setUpdateTime(enter.getLastUpdateTime());
        scooterRideStatDetail.setUpdateBy(enter.getInputUserId());
        return scooterRideStatDetail;
    }

    /**
     * buildCorDriverRideStat
     *
     * @param enter
     * @param driver
     * @param driverRideStatDetail
     * @return
     */
    private CorDriverRideStat buildCorDriverRideStat(SaveDeliveryStatEnter enter, CorDriver
            driver, CorDriverRideStatDetail driverRideStatDetail) {
        CorDriverRideStat insertDriverRideStat = new CorDriverRideStat();
        insertDriverRideStat.setId(idAppService.getId(SequenceName.COR_DRIVER_RIDE_STAT));
        insertDriverRideStat.setDr(0);
        insertDriverRideStat.setTenantId(enter.getInputTenantId());
        insertDriverRideStat.setDriverId(driver.getId());
        insertDriverRideStat.setCo2Total(driverRideStatDetail.getCo2Increment());
        insertDriverRideStat.setTotalDuration(enter.getDuration());
        insertDriverRideStat.setCo2Increment(driverRideStatDetail.getCo2Increment());
        insertDriverRideStat.setSavedMoney(driverRideStatDetail.getSavedMoney());
        insertDriverRideStat.setSvgSpeed(driverRideStatDetail.getSvgSpeed());
        insertDriverRideStat.setReadTime(enter.getLastUpdateTime());
        insertDriverRideStat.setTotalMileage(new BigDecimal(enter.getMileage()));
        insertDriverRideStat.setFirstRideTime(enter.getLastUpdateTime());
        insertDriverRideStat.setCreateTime(enter.getLastUpdateTime());
        insertDriverRideStat.setCreateBy(enter.getInputUserId());
        insertDriverRideStat.setUpdateTime(enter.getLastUpdateTime());
        insertDriverRideStat.setUpdateBy(enter.getInputUserId());
        return insertDriverRideStat;
    }

    /**
     * buildCorDriverRideStatDetailSingle
     *
     * @param enter
     * @param driverRideStat
     * @param driverId
     * @return
     */
    private CorDriverRideStatDetail buildCorDriverRideStatDetailSingle(SaveDeliveryStatEnter
                                                                               enter, CorDriverRideStat driverRideStat, Long driverId) {
        CorDriverRideStatDetail driverRideStatDetail = new CorDriverRideStatDetail();
        driverRideStatDetail.setId(idAppService.getId(SequenceName.COR_DRIVER_RIDE_STAT_DETAIL));
        driverRideStatDetail.setDr(0);
        driverRideStatDetail.setTenantId(enter.getInputTenantId());
        driverRideStatDetail.setBizId(enter.getBizId());
        driverRideStatDetail.setBizType(enter.getBizType());
        driverRideStatDetail.setDriverId(driverId);
        driverRideStatDetail.setDuration(enter.getDuration());
        driverRideStatDetail.setCo2HistoryTotal(driverRideStat == null ? new BigDecimal(0) : driverRideStat.getCo2Total());
        driverRideStatDetail.setCo2Increment(enter.getCo2());

        String avg = Double.toString(enter.getMileage() / (enter.getDuration() > 0 ? enter.getDuration() : 1));
        driverRideStatDetail.setSvgSpeed(new BigDecimal(avg));
        driverRideStatDetail.setMileage(new BigDecimal(enter.getMileage()));
        driverRideStatDetail.setSavedMoney(enter.getCo2());
        driverRideStatDetail.setCreateTime(enter.getLastUpdateTime());
        driverRideStatDetail.setCreateBy(enter.getInputUserId());
        driverRideStatDetail.setUpdateBy(enter.getInputUserId());
        driverRideStatDetail.setUpdateTime(enter.getLastUpdateTime());
        return driverRideStatDetail;
    }
}
