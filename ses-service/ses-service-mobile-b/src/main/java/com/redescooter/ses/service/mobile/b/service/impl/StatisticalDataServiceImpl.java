package com.redescooter.ses.service.mobile.b.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.b.service.StatisticalDataService;
import com.redescooter.ses.api.mobile.b.vo.SaveDriverRideStatEnter;
import com.redescooter.ses.api.mobile.b.vo.SaveScooterRideStatEnter;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverRideStatDetailMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverRideStatMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverScooterMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    /**
     * 保存司机骑行数据
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveDriverRideStat(SaveDriverRideStatEnter enter) {

//        // 查询司机之前是否存在有统计数据
//        QueryWrapper<CorDriver> queryDriverWrapper = new QueryWrapper<>();
//        queryDriverWrapper.eq(CorDriver.COL_USER_ID, enter.getUserId());
//        CorDriver driver = corDriverMapper.selectOne(queryDriverWrapper);
//
//        QueryWrapper<CorDriverRideStat> driverRideStatQueryWrapper = new QueryWrapper<>();
//        driverRideStatQueryWrapper.eq(CorDriverRideStat.COL_DRIVER_ID, driver.getId());
//        CorDriverRideStat driverRideStat = corDriverRideStatMapper.selectOne(driverRideStatQueryWrapper);
//
//        // 插入详情
//        CorDriverRideStatDetail driverRideStatDetail = buildDriverRideStatDetailSingle(enter, driverRideStat, driver.getId());
//        corDriverRideStatDetailMapper.insertOrUpdateSelective(driverRideStatDetail);
//
//        if (driverRideStat == null) {
//            DriverRideStat insertDriverRideStat = buildDriverRideStat(enter, driver, driverRideStatDetail);
//            driverRideStatMapper.insertOrUpdateSelective(insertDriverRideStat);
//        } else {
//            driverRideStat.setCo2Total(driverRideStat.getCo2Total().add(driverRideStatDetail.getCo2Increment()));
//            driverRideStat.setCo2Increment(driverRideStatDetail.getCo2Increment());
//            driverRideStat.setTotalDuration(driverRideStat.getTotalDuration() + enter.getDuration());
//            driverRideStat.setSavedMoney(driverRideStat.getSavedMoney().add(driverRideStatDetail.getSavedMoney()));
//            driverRideStat.setSvgSpeed(driverRideStat.getSvgSpeed().add(driverRideStatDetail.getSvgSpeed()));
//            driverRideStat.setTotalMileage(driverRideStat.getTotalMileage().add(driverRideStatDetail.getMileage()));
//            driverRideStat.setUpdateBy(enter.getUserId());
//            driverRideStat.setUpdateTime(new Date());
//            driverRideStatMapper.insertOrUpdateSelective(driverRideStat);
//        }
//        return new GeneralResult(enter.getRequestId());
        return null;
    }

    /**
     * 车辆统计数据
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveScooterRideStat(SaveScooterRideStatEnter enter) {
        return null;
    }

//    private DriverRideStatDetail buildDriverRideStatDetailSingle(SaveDriverRideStatEnter enter, CorDriverRideStat driverRideStat, Long driverId) {
//        CorDriverRideStatDetail driverRideStatDetail = new DriverRideStatDetail();
//        driverRideStatDetail.setId(idSerService.getId(SequenceName.DRIVER_RIDE_STAT_DETAIL));
//        driverRideStatDetail.setTenantId(enter.getTenantId());
//        driverRideStatDetail.setBizId(enter.getBizId());
//        driverRideStatDetail.setBizType(enter.getBizType());
//        driverRideStatDetail.setDriverId(driverId);
//        driverRideStatDetail.setDuration(enter.getDuration());
//        driverRideStatDetail.setCo2HistoryTotal(driverRideStat == null ? new BigDecimal(0) : driverRideStat.getCo2Total());
//        driverRideStatDetail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
//        driverRideStatDetail.setSvgSpeed(new BigDecimal(enter.getMileage() / enter.getDuration()));
//        driverRideStatDetail.setMileage(new BigDecimal(enter.getMileage()));
//        driverRideStatDetail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
//        driverRideStatDetail.setCreateTime(new Date());
//        driverRideStatDetail.setCreateBy(enter.getUserId());
//        driverRideStatDetail.setUpdateBy(enter.getUserId());
//        driverRideStatDetail.setUpdateTime(new Date());
//        return driverRideStatDetail;
//    }
}
