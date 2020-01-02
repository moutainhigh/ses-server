package com.redescooter.ses.service.mobile.b.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.service.StatisticalDataService;
import com.redescooter.ses.api.mobile.b.vo.MobileBDeliveryChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBScooterChartResult;
import com.redescooter.ses.api.mobile.b.vo.MonthlyScooterChartResult;
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
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.CO2MoneyConversionUtil;
import com.redescooter.ses.tool.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:StatisticalDataServiceImpl
 * @description: StatisticalDataServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 17:01
 */
@Service
public class StatisticalDataServiceImpl implements StatisticalDataService {

    @Autowired
    private CorDriverMapper corDriverMapper;

    @Autowired
    private CorDriverRideStatMapper corDriverRideStatMapper;

    @Autowired
    private CorDriverScooterMapper corDriverScooterMapper;

    @Autowired
    private CorDriverRideStatDetailMapper corDriverRideStatDetailMapper;

    @Autowired
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
    @Override
    public void saveDriverRideStat(List<SaveDeliveryStatEnter> enter) {

        List<CorDriverRideStatDetail> saveDriverRideStatDetailList = new ArrayList<>();

        List<CorDriverRideStat> saveDriverRideStatList = new ArrayList<>();

        List<CorDriverRideStat> updateDriverRideStatList = new ArrayList<>();

        // 查询司机之前是否存在有统计数据
        if (CollectionUtils.isNotEmpty(enter)) {
            enter.forEach(item -> {
                QueryWrapper<CorDriver> queryDriverWrapper = new QueryWrapper<>();
                queryDriverWrapper.eq(CorDriver.COL_USER_ID, item.getInputUserId());
                CorDriver driver = corDriverMapper.selectOne(queryDriverWrapper);

                QueryWrapper<CorDriverRideStat> driverRideStatQueryWrapper = new QueryWrapper<>();
                driverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DRIVER_ID, driver.getId());
                CorDriverRideStat driverRideStat = corDriverRideStatMapper.selectOne(driverRideStatQueryWrapper);

                // 插入详情
                CorDriverRideStatDetail driverRideStatDetail = buildCorDriverRideStatDetailSingle(item, driverRideStat, driver.getId());
                saveDriverRideStatDetailList.add(driverRideStatDetail);

                if (driverRideStat == null) {
                    saveDriverRideStatList.forEach(driverRide -> {
                        if (driverRide.getDriverId() != driver.getId()) {
                            saveDriverRideStatList.add(buildCorDriverRideStat(item, driver, driverRideStatDetail));
                        }
                    });
                } else {
                    driverRideStat.setCo2Total(driverRideStat.getCo2Total().add(driverRideStatDetail.getCo2Increment()));
                    driverRideStat.setCo2Increment(driverRideStatDetail.getCo2Increment());
                    driverRideStat.setTotalDuration(driverRideStat.getTotalDuration() + item.getDuration());
                    driverRideStat.setSavedMoney(driverRideStat.getSavedMoney().add(driverRideStatDetail.getSavedMoney()));
                    driverRideStat.setSvgSpeed(driverRideStat.getSvgSpeed().add(driverRideStatDetail.getSvgSpeed()));
                    driverRideStat.setTotalMileage(driverRideStat.getTotalMileage().add(driverRideStatDetail.getMileage()));
                    driverRideStat.setUpdateBy(item.getInputUserId());
                    driverRideStat.setUpdateTime(new Date());
                    updateDriverRideStatList.add(driverRideStat);
                }
            });
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
    @Override
    public void saveScooterRideStat(List<SaveDeliveryStatEnter> enter) {
        List<CorScooterRideStatDetail> saveCorScooterRideStatDetailList = new ArrayList<>();

        List<CorScooterRideStat> saveCorScooterRideStatList = new ArrayList<>();

        List<CorScooterRideStat> updateCorScooterRideStatList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(enter)) {
            enter.forEach(item -> {
                // 查询司机之前是否存在有统计数据
                QueryWrapper<CorDriverScooter> driverScooterQueryWrapper = new QueryWrapper<>();
                driverScooterQueryWrapper.eq(CorDriverScooter.COL_USER_ID, item.getInputUserId());
                driverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
                driverScooterQueryWrapper.in(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
                CorDriverScooter driverScooter = corDriverScooterMapper.selectOne(driverScooterQueryWrapper);

                QueryWrapper<CorScooterRideStat> scooterRideStatQueryWrapper = new QueryWrapper<>();
                scooterRideStatQueryWrapper.eq(CorScooterRideStat.COL_DR, 0);
                scooterRideStatQueryWrapper.eq(CorScooterRideStat.COL_SCOOTER_ID, driverScooter.getScooterId());
                CorScooterRideStat scooterRideStat = corScooterRideStatMapper.selectOne(scooterRideStatQueryWrapper);

                // 插入详情
                CorScooterRideStatDetail scooterRideStatDetail = buildScooterRideStatDetail(item, driverScooter, scooterRideStat);
                corScooterRideStatDetailMapper.insertOrUpdateSelective(scooterRideStatDetail);

                if (scooterRideStat == null) {
                    CorScooterRideStat insertScooterRideStat = buildScooterRideStat(item, driverScooter, scooterRideStatDetail);
                    corScooterRideStatMapper.insertOrUpdateSelective(insertScooterRideStat);
                } else {
                    scooterRideStat.setTotalDuration(scooterRideStat.getTotalDuration() + item.getDuration());
                    scooterRideStat.setCo2Total(scooterRideStat.getCo2Total().add(scooterRideStatDetail.getCo2Increment()));
                    scooterRideStat.setCo2Increment(scooterRideStatDetail.getCo2Increment());
                    scooterRideStat.setSavedMoney(scooterRideStat.getSavedMoney().add(scooterRideStatDetail.getSavedMoney()));
                    scooterRideStat.setSvgSpeed(scooterRideStat.getSvgSpeed().add(scooterRideStatDetail.getSvgSpeed()));
                    scooterRideStat.setTotalMileage(scooterRideStat.getTotalMileage().add(new BigDecimal(item.getMileage())));
                    corScooterRideStatMapper.updateById(scooterRideStat);
                }
            });

        }
    }

    /**
     * 订单数据图表统计
     *
     * @param enter
     * @return
     */
    @Override
    public MobileBDeliveryChartResult mobileBDeliveryChart(GeneralEnter enter) {
        // 获取指定 日期格式
        ArrayList<String> dayList = DateUtil.getDayList(30, DateUtil.DEFAULT_DATE_FORMAT);

        // 状态统计
        List<CountByStatusResult> list = deliveryServiceMapper.countByStatus(enter);

        // 拒绝的订单
        int count = deliveryServiceMapper.refuseDelivery(enter.getUserId(), DeliveryStatusEnums.REJECTED.getValue());
        list.add(CountByStatusResult.builder().status(DeliveryStatusEnums.REJECTED.getValue()).totalCount(count).build());
        Map<String, Integer> statusMap = new HashMap<>();
        for (CountByStatusResult item : list) {
            statusMap.put(item.getStatus(), item.getTotalCount());
        }
        for (DeliveryStatusEnums status : DeliveryStatusEnums.values()) {
            if (!statusMap.containsKey(status.getValue())) {
                statusMap.put(status.getValue(), 0);
            }
        }

        // 查询每天的订单数据


        return null;
    }

    /**
     * 车辆图表数据统计
     *
     * @param enter
     * @return
     */
    @Override
    public MobileBScooterChartResult mobileBScooterChart(GeneralEnter enter) {

        // 获取指定 日期格式
        ArrayList<String> dayList = DateUtil.getDayList(30, DateUtil.DEFAULT_DATE_FORMAT);

        // 查询司机Id
        QueryWrapper<CorDriver> corDriverQueryWrapper = new QueryWrapper<>();
        corDriverQueryWrapper.eq(CorDriver.COL_USER_ID, enter.getUserId());
        corDriverQueryWrapper.eq(CorDriver.COL_DR, 0);
        CorDriver corDriver = corDriverMapper.selectOne(corDriverQueryWrapper);

        QueryWrapper<CorDriverRideStat> corDriverRideStatQueryWrapper = new QueryWrapper<>();
        corDriverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DR, 0);
        corDriverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DRIVER_ID, corDriver.getId());
        CorDriverRideStat corDriverRideStat = corDriverRideStatMapper.selectOne(corDriverRideStatQueryWrapper);
        if (corDriverRideStat == null) {
            return new MobileBScooterChartResult();
        }

        // 查询每天的骑行数据
        List<MonthlyScooterChartResult> monthlyScooterChartResultList = statisticalDataServiceMapper.mobileBScooterChart(corDriver.getId(), enter.getTenantId());
        if (CollectionUtils.isEmpty(monthlyScooterChartResultList)) {
            return new MobileBScooterChartResult();
        }

        Map<String, MonthlyScooterChartResult> resultMap = new LinkedHashMap<>();
        for (String item : dayList) {
            Boolean map = Boolean.FALSE;
            for (MonthlyScooterChartResult chart : monthlyScooterChartResultList) {
                if (StringUtils.equals(item, chart.getTimes())) {
                    MonthlyScooterChartResult chartResult = chart;
                    resultMap.put(item, chartResult);
                    map = Boolean.TRUE;
                }
            }
            if (!map) {
                resultMap.put(item, new MonthlyScooterChartResult());
            }
        }
        return MobileBScooterChartResult.builder()
                .totalCo2(corDriverRideStat.getCo2Total().toString())
                .totalMileage(corDriverRideStat.getTotalMileage().toString())
                .totalMoney(corDriverRideStat.getSavedMoney().toString())
                .monthlyScooterResults(resultMap)
                .build();
    }

    private CorScooterRideStat buildScooterRideStat(SaveDeliveryStatEnter enter, CorDriverScooter driverScooter, CorScooterRideStatDetail scooterRideStatDetail) {
        CorScooterRideStat insertScooterRideStat = new CorScooterRideStat();
        insertScooterRideStat.setId(idAppService.getId(SequenceName.COR_SCOOTER_RIDE_STAT));
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

    private CorScooterRideStatDetail buildScooterRideStatDetail(SaveDeliveryStatEnter enter, CorDriverScooter driverScooter, CorScooterRideStat scooterRideStat) {
        CorScooterRideStatDetail scooterRideStatDetail = new CorScooterRideStatDetail();
        scooterRideStatDetail.setId(idAppService.getId(SequenceName.COR_SCOOTER_RIDE_STAT_DETAIL));
        scooterRideStatDetail.setTenantId(enter.getInputTenantId());
        scooterRideStatDetail.setBizId(enter.getBizId());
        scooterRideStatDetail.setBizType(enter.getBizType());
        scooterRideStatDetail.setScooterId(driverScooter.getScooterId());
        scooterRideStatDetail.setDuration(enter.getDuration());
        scooterRideStatDetail.setCo2HistoryTotal(scooterRideStat == null ? new BigDecimal(0d) : scooterRideStat.getCo2Total());
        scooterRideStatDetail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
        String avg = Double.toString(enter.getMileage() / (enter.getDuration() > 0 ? enter.getDuration() : 1));
        scooterRideStatDetail.setSvgSpeed(new BigDecimal(avg));
        scooterRideStatDetail.setMileage(new BigDecimal(enter.getMileage()));
        scooterRideStatDetail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
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
    private CorDriverRideStatDetail buildCorDriverRideStatDetailSingle(SaveDeliveryStatEnter enter, CorDriverRideStat driverRideStat, Long driverId) {
        CorDriverRideStatDetail driverRideStatDetail = new CorDriverRideStatDetail();
        driverRideStatDetail.setId(idAppService.getId(SequenceName.COR_DRIVER_RIDE_STAT_DETAIL));
        driverRideStatDetail.setDr(0);
        driverRideStatDetail.setTenantId(enter.getInputTenantId());
        driverRideStatDetail.setBizId(enter.getBizId());
        driverRideStatDetail.setBizType(enter.getBizType());
        driverRideStatDetail.setDriverId(driverId);
        driverRideStatDetail.setDuration(enter.getDuration());
        driverRideStatDetail.setCo2HistoryTotal(driverRideStat == null ? new BigDecimal(0) : driverRideStat.getCo2Total());
        driverRideStatDetail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));

        String avg = Double.toString(enter.getMileage() / (enter.getDuration() > 0 ? enter.getDuration() : 1));
        driverRideStatDetail.setSvgSpeed(new BigDecimal(avg));
        driverRideStatDetail.setMileage(new BigDecimal(enter.getMileage()));
        driverRideStatDetail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
        driverRideStatDetail.setCreateTime(enter.getLastUpdateTime());
        driverRideStatDetail.setCreateBy(enter.getInputUserId());
        driverRideStatDetail.setUpdateBy(enter.getInputUserId());
        driverRideStatDetail.setUpdateTime(enter.getLastUpdateTime());
        return driverRideStatDetail;
    }
}
