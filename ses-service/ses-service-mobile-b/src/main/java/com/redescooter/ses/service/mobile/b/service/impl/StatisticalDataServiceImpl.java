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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName:StatisticalDataServiceImpl
 * @description: StatisticalDataServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2019/12/30 17:01
 */
@Slf4j
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
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void saveDriverRideStat(List<SaveDeliveryStatEnter> enter) {

        // ????????????
        List<CorDriverRideStatDetail> detailList = new ArrayList<>();
        // ????????????
        List<CorDriverRideStat> saveList = new ArrayList<>();
        // ????????????
        List<CorDriverRideStat> updateList = new ArrayList<>();

        // ?????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(enter)) {
            for (SaveDeliveryStatEnter item : enter) {
                QueryWrapper<CorDriver> wrapper = new QueryWrapper<>();
                wrapper.eq(CorDriver.COL_USER_ID, item.getInputUserId());
                wrapper.eq(CorDriver.COL_DR, 0);
                wrapper.last("limit 1");
                CorDriver driver = corDriverMapper.selectOne(wrapper);

                QueryWrapper<CorDriverRideStat> qw = new QueryWrapper<>();
                qw.eq(CorDriverRideStat.COL_DRIVER_ID, driver.getId());
                qw.eq(CorDriverRideStat.COL_DR, 0);
                qw.last("limit 1");
                CorDriverRideStat driverRideStat = corDriverRideStatMapper.selectOne(qw);

                Boolean flag = false;
                if (CollectionUtils.isNotEmpty(saveList)) {
                    Iterator<CorDriverRideStat> iterator = saveList.iterator();
                    while (iterator.hasNext()) {
                        CorDriverRideStat next = iterator.next();
                        if (next.getDriverId().equals(driver.getId())) {
                            driverRideStat = next;
                            flag = true;
                            iterator.remove();
                            break;
                        }
                    }
                }

                // ????????????
                CorDriverRideStatDetail detail = buildCorDriverRideStatDetailSingle(item, driverRideStat, driver.getId());
                detailList.add(detail);

                if (driverRideStat == null) {
                    saveList.add(buildCorDriverRideStat(item, driver, detail));
                } else {
                    driverRideStat.setCo2Total(driverRideStat.getCo2Total().add(detail.getCo2Increment()));
                    driverRideStat.setCo2Increment(detail.getCo2Increment());
                    driverRideStat.setTotalDuration(driverRideStat.getTotalDuration() + item.getDuration());
                    driverRideStat.setSavedMoney(driverRideStat.getSavedMoney().add(detail.getSavedMoney()));
                    driverRideStat.setSvgSpeed(driverRideStat.getSvgSpeed().add(detail.getSvgSpeed()));
                    driverRideStat.setTotalMileage(driverRideStat.getTotalMileage().add(detail.getMileage()));
                    driverRideStat.setUpdateBy(item.getInputUserId());
                    driverRideStat.setUpdateTime(new Date());
                    if (flag) {
                        saveList.add(driverRideStat);
                    } else {
                        updateList.add(driverRideStat);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(detailList)) {
            corDriverRideStatDetailMapper.batchInsert(detailList);
        }
        if (CollectionUtils.isNotEmpty(saveList)) {
            corDriverRideStatMapper.batchInsert(saveList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            corDriverRideStatMapper.batchInsert(updateList);
        }
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void saveScooterRideStat(List<SaveDeliveryStatEnter> enter) {

        // ????????????
        List<CorScooterRideStatDetail> detailList = new ArrayList<>();
        // ????????????
        List<CorScooterRideStat> saveList = new ArrayList<>();
        // ????????????
        List<CorScooterRideStat> updateList = new ArrayList<>();

        // ?????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(enter)) {
            for (SaveDeliveryStatEnter item : enter) {

                QueryWrapper<CorDriver> wrapper = new QueryWrapper<>();
                wrapper.eq(CorDriver.COL_USER_ID, item.getInputUserId());
                wrapper.eq(CorDriver.COL_DR, 0);
                wrapper.last("limit 1");
                CorDriver corDriver = corDriverMapper.selectOne(wrapper);

                QueryWrapper<CorDriverScooter> qw = new QueryWrapper<>();
                qw.eq(CorDriverScooter.COL_DRIVER_ID, corDriver.getId());
                qw.eq(CorDriverScooter.COL_DR, 0);
                qw.in(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
                qw.last("limit 1");
                CorDriverScooter driverScooter = corDriverScooterMapper.selectOne(qw);

                QueryWrapper<CorScooterRideStat> lqw = new QueryWrapper<>();
                lqw.eq(CorScooterRideStat.COL_DR, 0);
                lqw.eq(CorScooterRideStat.COL_SCOOTER_ID, driverScooter.getScooterId());
                lqw.last("limit 1");
                CorScooterRideStat scooterRideStat = corScooterRideStatMapper.selectOne(lqw);

                Boolean flag = false;
                if (CollectionUtils.isNotEmpty(saveList)) {
                    Iterator<CorScooterRideStat> iterator = saveList.iterator();
                    while (iterator.hasNext()) {
                        CorScooterRideStat next = iterator.next();
                        if (next.getScooterId().equals(driverScooter.getScooterId())) {
                            scooterRideStat = next;
                            flag = true;
                            iterator.remove();
                            break;
                        }
                    }
                }

                // ????????????
                CorScooterRideStatDetail detail = buildScooterRideStatDetail(item, driverScooter, scooterRideStat);
                detailList.add(detail);

                if (scooterRideStat == null) {
                    saveList.add(buildScooterRideStat(item, driverScooter, detail));
                } else {
                    scooterRideStat.setTotalDuration(scooterRideStat.getTotalDuration() + item.getDuration());
                    scooterRideStat.setCo2Total(scooterRideStat.getCo2Total().add(detail.getCo2Increment()));
                    scooterRideStat.setCo2Increment(detail.getCo2Increment());
                    scooterRideStat.setSavedMoney(scooterRideStat.getSavedMoney().add(detail.getSavedMoney()));
                    scooterRideStat.setSvgSpeed(scooterRideStat.getSvgSpeed().add(detail.getSvgSpeed()));
                    scooterRideStat.setTotalMileage(scooterRideStat.getTotalMileage().add(new BigDecimal(item.getMileage())));
                    if (flag) {
                        saveList.add(scooterRideStat);
                    } else {
                        updateList.add(scooterRideStat);
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(detailList)) {
            corScooterRideStatDetailMapper.batchInsert(detailList);
        }
        if (CollectionUtils.isNotEmpty(saveList)) {
            corScooterRideStatMapper.batchInsert(saveList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            corScooterRideStatMapper.updateBatch(updateList);
        }
    }

    /**
     * ????????????????????????
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
        // ??????????????????????????????N???????????????
//            dayList = DateUtil.getDayList(enter.getDateTime() == null ? new Date() : enter.getDateTime(), 30, null);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(enter.getEndDateTime());
        calendar.add(calendar.DATE,1); //???????????????????????????,??????  ?????????,??????????????????
        dayList = DateUtil.getBetweenDates(enter.getStartDateTime(), calendar.getTime());
        List<MonthlyDeliveryChartResult> list = deliveryServiceMapper.mobileBDeliveryChart(enter);

        MonthlyDeliveryChartResult result = null;
        if (CollectionUtils.isEmpty(list)) {
            for (String str : dayList) {
                result = new MonthlyDeliveryChartResult();
                result.setTimes(str);
                allMap.put(str, result);
            }
        } else {
            // ??????????????????????????????N???????????????
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
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public AllMobileBScooterChartResult mobileBScooterChart(DateTimeParmEnter enter) {

        Map<String, MobileBScooterChartResult> map = new LinkedHashMap<>();
        // ????????????Id
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
// ??????????????????????????????N???????????????
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
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public MobileBScooterChartResult mobileBAllScooterChart(GeneralEnter enter) {
        MobileBScooterChartResult result = new MobileBScooterChartResult();
        // ????????????Id
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
    private CorDriverRideStat buildCorDriverRideStat(SaveDeliveryStatEnter enter, CorDriver driver, CorDriverRideStatDetail driverRideStatDetail) {
        CorDriverRideStat result = new CorDriverRideStat();
        result.setId(idAppService.getId(SequenceName.COR_DRIVER_RIDE_STAT));
        result.setDr(0);
        result.setTenantId(enter.getInputTenantId());
        result.setDriverId(driver.getId());
        result.setCo2Total(driverRideStatDetail.getCo2Increment());
        result.setTotalDuration(enter.getDuration());
        result.setCo2Increment(driverRideStatDetail.getCo2Increment());
        result.setSavedMoney(driverRideStatDetail.getSavedMoney());
        result.setSvgSpeed(driverRideStatDetail.getSvgSpeed());
        result.setReadTime(enter.getLastUpdateTime());
        result.setTotalMileage(new BigDecimal(enter.getMileage()));
        result.setFirstRideTime(enter.getLastUpdateTime());
        result.setCreateTime(enter.getLastUpdateTime());
        result.setCreateBy(enter.getInputUserId());
        result.setUpdateTime(enter.getLastUpdateTime());
        result.setUpdateBy(enter.getInputUserId());
        return result;
    }

    /**
     * buildCorDriverRideStatDetailSingle
     *
     * @param enter
     * @param driverRideStat
     * @param driverId
     * @return
     */
    private CorDriverRideStatDetail buildCorDriverRideStatDetailSingle(SaveDeliveryStatEnter enter, CorDriverRideStat driverRideStat, Long driverId) {
        CorDriverRideStatDetail result = new CorDriverRideStatDetail();
        result.setId(idAppService.getId(SequenceName.COR_DRIVER_RIDE_STAT_DETAIL));
        result.setDr(0);
        result.setTenantId(enter.getInputTenantId());
        result.setBizId(enter.getBizId());
        result.setBizType(enter.getBizType());
        result.setDriverId(driverId);
        result.setDuration(enter.getDuration());
        result.setCo2HistoryTotal(driverRideStat == null ? new BigDecimal("0") : driverRideStat.getCo2Total());
        result.setCo2Increment(enter.getCo2());

        String avg = Double.toString(enter.getMileage() / (enter.getDuration() > 0 ? enter.getDuration() : 1));
        result.setSvgSpeed(new BigDecimal(avg));
        result.setMileage(new BigDecimal(enter.getMileage()));
        result.setSavedMoney(enter.getCo2());
        result.setCreateTime(enter.getLastUpdateTime());
        result.setCreateBy(enter.getInputUserId());
        result.setUpdateBy(enter.getInputUserId());
        result.setUpdateTime(enter.getLastUpdateTime());
        return result;
    }
}
