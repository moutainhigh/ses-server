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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author assert
 * @date 2020/11/26 10:34
 */
@Slf4j
@DubboService
public class RideStatCServiceImpl implements RideStatCService {

    @Resource
    private DriverRideStatMapper driverRideStatMapper;

    @Resource
    private DriverRideStatDetailMapper driverRideStatDetailMapper;

    @Resource
    private ScooterRideStatMapper scooterRideStatMapper;

    @Resource
    private ScooterRideStatDetailMapper scooterRideStatDetailMapper;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 新增/修改 司机骑行数据/车辆骑行数据
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int insertDriverAndScooterRideStat(InsertRideStatDataDTO enter) {
        enter.setDuration(enter.getDuration() == 0 ? 1 : enter.getDuration());
        enter.setMileage(enter.getMileage().equals(BigDecimal.ZERO) ? BigDecimal.ONE : enter.getMileage());

        try {
            ConDriverRideStat model = new ConDriverRideStat();
            model.setUpdateBy(enter.getUserId());
            model.setUpdateTime(new Date());

            ConDriverRideStatDetail detail = new ConDriverRideStatDetail();

            /**
             * 检查司机骑行统计数据是否存在,存在则更新反之新增
             */
            ConDriverRideStat stat = driverRideStatMapper.getDriverRideStatByTenantIdAndDriverId(enter.getTenantId(), enter.getUserId());
            if (null != stat) {
                log.info("存在,更新");
                model.setId(stat.getId());
                model.setTotalDuration(stat.getTotalDuration() + enter.getDuration());
                model.setCo2Total(stat.getCo2Total().add(
                        new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())))
                );
                model.setCo2Increment(stat.getCo2Increment().add(
                        new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())))
                );
                model.setSavedMoney(stat.getSavedMoney().add(
                        new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())))
                );
                // 总公里/总时长
                BigDecimal svg = (stat.getTotalMileage().add(enter.getMileage())).divide(
                        new BigDecimal(stat.getTotalDuration() + enter.getDuration()), BigDecimal.ROUND_HALF_UP);
                model.setSvgSpeed(svg);
                driverRideStatMapper.updateDriverRideStat(model);

                // set detail Co2HistoryTotal
                detail.setCo2HistoryTotal(model.getCo2Total());
            } else {
                log.info("不存在,新增");
                model.setId(idAppService.getId(SequenceName.CON_DRIVER_RIDE_STAT));
                model.setTenantId(enter.getTenantId());
                model.setDriverId(enter.getUserId());
                model.setTotalDuration(enter.getDuration());
                model.setCo2Total(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
                model.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
                model.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
                model.setSvgSpeed(enter.getMileage().divide(new BigDecimal(enter.getDuration()), BigDecimal.ROUND_HALF_UP));
                model.setTotalMileage(enter.getMileage());
                model.setFirstRideTime(new Date());
                model.setCreateBy(enter.getUserId());
                model.setCreateTime(new Date());
                driverRideStatMapper.insertDriverRideStat(model);

                // set detail Co2HistoryTotal
                detail.setCo2HistoryTotal(BigDecimal.ZERO);
            }

            /**
             * 新增司机骑行统计数据详情
             */
            detail.setId(idAppService.getId(SequenceName.CON_DRIVER_RIDE_STAT_DETAIL));
            detail.setTenantId(enter.getTenantId());
            detail.setBizId(enter.getBizId());
            detail.setBizType(enter.getBizType());
            detail.setDriverId(enter.getUserId());
            detail.setDuration(enter.getDuration());
            detail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
            detail.setSvgSpeed(enter.getMileage().divide(new BigDecimal(enter.getDuration()), BigDecimal.ROUND_HALF_UP));
            detail.setMileage(enter.getMileage());
            detail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
            detail.setCreateBy(enter.getUserId());
            detail.setCreateTime(new Date());
            detail.setUpdateBy(enter.getUserId());
            detail.setUpdateTime(new Date());
            driverRideStatDetailMapper.insertDriverRideStatDetail(detail);

            /**
             * 保存车辆骑行数据和详情
             */
            insertScooterRideStatAndDetail(enter);
        } catch (Exception e) {
            log.error("【保存司机/车辆骑行统计数据和详情失败】----{}", ExceptionUtils.getStackTrace(e));
        }
        return 1;
    }


    /**
     * 保存车辆骑行数据(本来想直接把司机的骑行数据直接copy过来的,但是怕导致最后数据不一致就不copy了)
     * @param enter
     */
    private void insertScooterRideStatAndDetail(InsertRideStatDataDTO enter) {
        ConScooterRideStat model = new ConScooterRideStat();
        model.setUpdateBy(enter.getUserId());
        model.setUpdateTime(new Date());

        ConScooterRideStatDetail detail = new ConScooterRideStatDetail();

        /**
         * 检查车辆骑行数据是否存在,存在则更新反之新增
         */
        ConScooterRideStat stat = scooterRideStatMapper.getScooterRideStatByTenantIdAndScooterId(enter.getTenantId(), enter.getBizId());
        if (null != stat) {
            log.info("scooter存在,更新");
            model.setId(stat.getId());
            model.setTotalDuration(stat.getTotalDuration() + enter.getDuration());
            model.setCo2Total(stat.getCo2Total().add(
                    new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())))
            );
            model.setCo2Increment(stat.getCo2Increment().add(
                    new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())))
            );
            model.setSavedMoney(stat.getSavedMoney().add(
                    new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())))
            );
            BigDecimal svg = (stat.getTotalMileage().add(enter.getMileage())).divide(
                    new BigDecimal(stat.getTotalDuration() + enter.getDuration()), BigDecimal.ROUND_HALF_UP);
            model.setSvgSpeed(svg);
            scooterRideStatMapper.updateScooterRideStat(model);

            // set detail Co2HistoryTotal
            detail.setCo2HistoryTotal(model.getCo2Total());
        } else {
            log.info("scooter不存在,新增");
            model.setId(idAppService.getId(SequenceName.CON_SCOOTER_RIDE_STAT));
            model.setTenantId(enter.getTenantId());
            model.setScooterId(enter.getBizId());
            model.setTotalDuration(enter.getDuration());
            model.setCo2Total(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
            model.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
            model.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
            model.setSvgSpeed(enter.getMileage().divide(new BigDecimal(enter.getDuration()), BigDecimal.ROUND_HALF_UP));
            model.setTotalMileage(enter.getMileage());
            model.setFirstRideTime(new Date());
            model.setCreateBy(enter.getUserId());
            model.setCreateTime(new Date());
            scooterRideStatMapper.insertScooterRideStat(model);

            // set detail Co2HistoryTotal
            detail.setCo2HistoryTotal(BigDecimal.ZERO);
        }

        /**
         * 新增车辆骑行统计数据详情
         */
        detail.setId(idAppService.getId(SequenceName.CON_SCOOTER_RIDE_STAT_DETAIL));
        detail.setTenantId(enter.getTenantId());
        detail.setBizId(enter.getBizId());
        detail.setBizType(enter.getBizType());
        detail.setScooterId(enter.getBizId());
        detail.setDuration(enter.getDuration());
        detail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
        detail.setSvgSpeed(enter.getMileage().divide(new BigDecimal(enter.getDuration()), BigDecimal.ROUND_HALF_UP));
        detail.setMileage(enter.getMileage());
        detail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
        detail.setCreateBy(enter.getUserId());
        detail.setCreateTime(new Date());
        detail.setUpdateBy(enter.getUserId());
        detail.setUpdateTime(new Date());
        scooterRideStatDetailMapper.insertScooterRideStatDetail(detail);
    }

}
