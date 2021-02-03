package com.redescooter.ses.service.mobile.c.service;

import com.redescooter.ses.api.common.vo.scooter.InsertRideStatDataDTO;
import com.redescooter.ses.api.mobile.c.service.RideStatCService;
import com.redescooter.ses.service.mobile.c.constant.SequenceName;
import com.redescooter.ses.service.mobile.c.dao.DriverRideStatDetailMapper;
import com.redescooter.ses.service.mobile.c.dao.DriverRideStatMapper;
import com.redescooter.ses.service.mobile.c.dao.ScooterRideStatDetailMapper;
import com.redescooter.ses.service.mobile.c.dao.ScooterRideStatMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStat;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStatDetail;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStatDetail;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.co2.CO2MoneyConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author assert
 * @date 2020/11/26 10:34
 */
@Slf4j
@Service
public class RideStatCServiceImpl implements RideStatCService {

    @Resource
    private DriverRideStatMapper driverRideStatMapper;
    @Resource
    private DriverRideStatDetailMapper driverRideStatDetailMapper;
    @Resource
    private ScooterRideStatMapper scooterRideStatMapper;
    @Resource
    private ScooterRideStatDetailMapper scooterRideStatDetailMapper;
    @Resource
    private IdAppService idAppService;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public int insertDriverAndScooterRideStat(InsertRideStatDataDTO rideStatData) {
        rideStatData.setDuration(rideStatData.getDuration() == 0 ? 1 : rideStatData.getDuration());
        rideStatData.setMileage(rideStatData.getMileage().equals(BigDecimal.ZERO) ? BigDecimal.ONE : rideStatData.getMileage());

        transactionTemplate.execute(rideStatStatus -> {
            try {
                ConDriverRideStat driverRideStatNew = new ConDriverRideStat();
                driverRideStatNew.setUpdateBy(rideStatData.getUserId());
                driverRideStatNew.setUpdateTime(new Date());

                ConDriverRideStatDetail driverRideStatDetail = new ConDriverRideStatDetail();

                /**
                 * 检查司机骑行统计数据是否存在,存在则更新反之新增
                 */
                ConDriverRideStat driverRideStat = driverRideStatMapper.getDriverRideStatByTenantIdAndDriverId(rideStatData.getTenantId(),
                        rideStatData.getUserId());
                if (null != driverRideStat) {
                    driverRideStatNew.setId(driverRideStat.getId());
                    driverRideStatNew.setTotalDuration(rideStatData.getDuration() + driverRideStat.getTotalDuration());
                    driverRideStatNew.setCo2Total(driverRideStat.getCo2Total().add(
                            new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())))
                    );
                    driverRideStatNew.setCo2Increment(driverRideStat.getCo2Increment().add(
                            new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())))
                    );
                    driverRideStatNew.setSavedMoney(driverRideStat.getSavedMoney().add(
                            new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(rideStatData.getMileage().longValue())))
                    );
                    BigDecimal svg = (driverRideStat.getTotalMileage().add(rideStatData.getMileage())).divide(
                            new BigDecimal(driverRideStat.getTotalDuration() + rideStatData.getDuration()),BigDecimal.ROUND_HALF_UP);
                    driverRideStatNew.setSvgSpeed(svg);
                    driverRideStatMapper.updateDriverRideStat(driverRideStatNew);

                    // set detail Co2HistoryTotal
                    driverRideStatDetail.setCo2HistoryTotal(driverRideStatNew.getCo2Total());
                } else {
                    driverRideStatNew.setId(idAppService.getId(SequenceName.CON_DRIVER_RIDE_STAT));
                    driverRideStatNew.setTenantId(rideStatData.getTenantId());
                    driverRideStatNew.setDriverId(rideStatData.getUserId());
                    driverRideStatNew.setTotalDuration(rideStatData.getDuration());
                    driverRideStatNew.setCo2Total(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())));
                    driverRideStatNew.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())));
                    driverRideStatNew.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(rideStatData.getMileage().longValue())));
                    driverRideStatNew.setSvgSpeed(rideStatData.getMileage().divide(new BigDecimal(rideStatData.getDuration()),BigDecimal.ROUND_HALF_UP));
                    driverRideStatNew.setTotalMileage(rideStatData.getMileage());
                    driverRideStatNew.setFirstRideTime(new Date());
                    driverRideStatNew.setCreateBy(rideStatData.getUserId());
                    driverRideStatNew.setCreateTime(new Date());
                    driverRideStatMapper.insertDriverRideStat(driverRideStatNew);

                    // set detail Co2HistoryTotal
                    driverRideStatDetail.setCo2HistoryTotal(BigDecimal.ZERO);
                }

                /**
                 * 新增司机骑行统计数据详情
                 */
                driverRideStatDetail.setId(idAppService.getId(SequenceName.CON_DRIVER_RIDE_STAT_DETAIL));
                driverRideStatDetail.setTenantId(rideStatData.getTenantId());
                driverRideStatDetail.setBizId(rideStatData.getBizId());
                driverRideStatDetail.setBizType(rideStatData.getBizType());
                driverRideStatDetail.setDriverId(rideStatData.getUserId());
                driverRideStatDetail.setDuration(rideStatData.getDuration());
                driverRideStatDetail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())));
                driverRideStatDetail.setSvgSpeed(rideStatData.getMileage().divide(new BigDecimal(rideStatData.getDuration()),BigDecimal.ROUND_HALF_UP));
                driverRideStatDetail.setMileage(rideStatData.getMileage());
                driverRideStatDetail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(rideStatData.getMileage().longValue())));
                driverRideStatDetail.setCreateBy(rideStatData.getUserId());
                driverRideStatDetail.setCreateTime(new Date());
                driverRideStatDetail.setUpdateBy(rideStatData.getUserId());
                driverRideStatDetail.setUpdateTime(new Date());
                driverRideStatDetailMapper.insertDriverRideStatDetail(driverRideStatDetail);

                /**
                 * 保存车辆骑行数据和详情
                 */
                insertScooterRideStatAndDetail(rideStatData);
            } catch (Exception e) {
                log.error("【保存司机/车辆骑行统计数据和详情失败】----{}", ExceptionUtils.getStackTrace(e));
                rideStatStatus.setRollbackOnly();
            }
            return 1;
        });

        return 0;
    }


    /**
     * 保存车辆骑行数据(本来想直接把司机的骑行数据直接copy过来的,但是怕导致最后数据不一致就不copy了)
     * @param rideStatData
     */
    private void insertScooterRideStatAndDetail(InsertRideStatDataDTO rideStatData) {
        ConScooterRideStat scooterRideStatNew = new ConScooterRideStat();
        scooterRideStatNew.setUpdateBy(rideStatData.getUserId());
        scooterRideStatNew.setUpdateTime(new Date());

        ConScooterRideStatDetail scooterRideStatDetail = new ConScooterRideStatDetail();

        /**
         * 检查车辆骑行数据是否存在,存在则更新反之新增
         */
        ConScooterRideStat scooterRideStat = scooterRideStatMapper.getScooterRideStatByTenantIdAndScooterId(rideStatData.getTenantId(),
                rideStatData.getBizId());
        if (null != scooterRideStat) {
            scooterRideStatNew.setId(scooterRideStat.getId());
            scooterRideStatNew.setTotalDuration(rideStatData.getDuration() + scooterRideStat.getTotalDuration());
            scooterRideStatNew.setCo2Total(scooterRideStat.getCo2Total().add(
                    new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())))
            );
            scooterRideStatNew.setCo2Increment(scooterRideStat.getCo2Increment().add(
                    new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())))
            );
            scooterRideStatNew.setSavedMoney(scooterRideStat.getSavedMoney().add(
                    new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(rideStatData.getMileage().longValue())))
            );
            BigDecimal svg = (scooterRideStatNew.getTotalMileage().add(rideStatData.getMileage())).divide(
                    new BigDecimal(scooterRideStat.getTotalDuration() + rideStatData.getDuration()),BigDecimal.ROUND_HALF_UP);
            scooterRideStatNew.setSvgSpeed(svg);
            scooterRideStatMapper.updateScooterRideStat(scooterRideStatNew);

            // set detail Co2HistoryTotal
            scooterRideStatDetail.setCo2HistoryTotal(scooterRideStatNew.getCo2Total());
        } else {
            scooterRideStatNew.setId(idAppService.getId(SequenceName.CON_SCOOTER_RIDE_STAT));
            scooterRideStatNew.setTenantId(rideStatData.getTenantId());
            scooterRideStatNew.setScooterId(rideStatData.getBizId());
            scooterRideStatNew.setTotalDuration(rideStatData.getDuration());
            scooterRideStatNew.setCo2Total(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())));
            scooterRideStatNew.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())));
            scooterRideStatNew.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(rideStatData.getMileage().longValue())));
            scooterRideStatNew.setSvgSpeed(rideStatData.getMileage().divide(new BigDecimal(rideStatData.getDuration()),BigDecimal.ROUND_HALF_UP));
            scooterRideStatNew.setTotalMileage(rideStatData.getMileage());
            scooterRideStatNew.setFirstRideTime(new Date());
            scooterRideStatNew.setCreateBy(rideStatData.getUserId());
            scooterRideStatNew.setCreateTime(new Date());
            scooterRideStatMapper.insertScooterRideStat(scooterRideStatNew);

            // set detail Co2HistoryTotal
            scooterRideStatDetail.setCo2HistoryTotal(BigDecimal.ZERO);
        }

        /**
         * 新增车辆骑行统计数据详情
         */
        scooterRideStatDetail.setId(idAppService.getId(SequenceName.CON_SCOOTER_RIDE_STAT_DETAIL));
        scooterRideStatDetail.setTenantId(rideStatData.getTenantId());
        scooterRideStatDetail.setBizId(rideStatData.getBizId());
        scooterRideStatDetail.setBizType(rideStatData.getBizType());
        scooterRideStatDetail.setScooterId(rideStatData.getBizId());
        scooterRideStatDetail.setDuration(rideStatData.getDuration());
        scooterRideStatDetail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(rideStatData.getMileage().longValue())));
        scooterRideStatDetail.setSvgSpeed(rideStatData.getMileage().divide(new BigDecimal(rideStatData.getDuration()),BigDecimal.ROUND_HALF_UP));
        scooterRideStatDetail.setMileage(rideStatData.getMileage());
        scooterRideStatDetail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(rideStatData.getMileage().longValue())));
        scooterRideStatDetail.setCreateBy(rideStatData.getUserId());
        scooterRideStatDetail.setCreateTime(new Date());
        scooterRideStatDetail.setUpdateBy(rideStatData.getUserId());
        scooterRideStatDetail.setUpdateTime(new Date());
        scooterRideStatDetailMapper.insertScooterRideStatDetail(scooterRideStatDetail);

    }

}
