package com.redescooter.ses.service.mobile.c.service;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.service.RideScooterDateService;
import com.redescooter.ses.api.mobile.c.vo.SaveRideDateEnter;
import com.redescooter.ses.service.mobile.c.constant.SequenceName;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStat;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStatDetail;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStatDetail;
import com.redescooter.ses.service.mobile.c.service.base.ConDriverRideStatDetailService;
import com.redescooter.ses.service.mobile.c.service.base.ConDriverRideStatService;
import com.redescooter.ses.service.mobile.c.service.base.ConScooterRideStatDetailService;
import com.redescooter.ses.service.mobile.c.service.base.ConScooterRideStatService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.CO2MoneyConversionUtil;

/**
 * @ClassName:RideScooterDateServiceImpl
 * @description: RideScooterDateServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 16:02
 */
@Service
public class RideScooterDateServiceImpl implements RideScooterDateService{

    @Autowired
    private ConDriverRideStatService conDriverRideStatService;

    @Autowired
    private ConDriverRideStatDetailService conDriverRideStatDetailService;

    @Autowired
    private ConScooterRideStatService conScooterRideStatService;

    @Autowired
    private ConScooterRideStatDetailService conScooterRideStatDetailService;

    @Autowired
    private IdAppService idAppService;

    /**
     * @param enter
     * @desc: 司机骑行数据
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/21 11:45
     * @Version: APP 1.2
     */
    @Override
    public GeneralResult saveDriverRideDate(SaveRideDateEnter enter) {
        // 查询司机之前是否存在有统计数据
        QueryWrapper<ConDriverRideStat> conDriverRideStatQueryWrapper=new QueryWrapper<>();
        conDriverRideStatQueryWrapper.eq(ConDriverRideStat.COL_TENANT_ID,enter.getTenantId());
        conDriverRideStatQueryWrapper.eq(ConDriverRideStat.COL_DRIVER_ID,enter.getUserId());
        conDriverRideStatQueryWrapper.eq(ConDriverRideStat.COL_DR,0);
        ConDriverRideStat conDriverRideStat = conDriverRideStatService.getOne(conDriverRideStatQueryWrapper);

        enter.setDuration(enter.getDuration()==0?1:enter.getDuration());
        enter.setMileage(enter.getMileage().equals(BigDecimal.ZERO) ? new BigDecimal("1"):enter.getMileage());

        if (conDriverRideStat==null){
            // 重新生成
            conDriverRideStat=new ConDriverRideStat();
            conDriverRideStat.setId(idAppService.getId(SequenceName.CON_DRIVER_RIDE_STAT));
            conDriverRideStat.setDr(0);
            conDriverRideStat.setTenantId(enter.getTenantId());
            conDriverRideStat.setDriverId(enter.getUserId());
            conDriverRideStat.setTotalDuration(enter.getDuration());
            conDriverRideStat.setCo2Total(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
            conDriverRideStat.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
            conDriverRideStat.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
            conDriverRideStat.setSvgSpeed(enter.getMileage().divide(new BigDecimal(enter.getDuration())));
            conDriverRideStat.setReadTime(null);
            conDriverRideStat.setTotalMileage(enter.getMileage());
            conDriverRideStat.setFirstRideTime(new Date());
            conDriverRideStat.setCreateBy(enter.getUserId());
            conDriverRideStat.setCreateTime(new Date());
        }else {
            //进行数据累加
            conDriverRideStat.setTotalDuration(enter.getDuration()+conDriverRideStat.getTotalDuration());
            conDriverRideStat.setCo2Total(conDriverRideStat.getCo2Total().add(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue()))));
            conDriverRideStat.setCo2Increment(conDriverRideStat.getCo2Increment().add(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue()))));
            conDriverRideStat.setSavedMoney(conDriverRideStat.getSavedMoney().add(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue()))));
            BigDecimal svg=(conDriverRideStat.getTotalMileage().add(enter.getMileage())).divide(new BigDecimal(conDriverRideStat.getTotalDuration()+enter.getDuration()));
            conDriverRideStat.setSvgSpeed(svg);
            conDriverRideStat.setUpdateBy(enter.getUserId());
            conDriverRideStat.setUpdateTime(new Date());
        }
        conDriverRideStatService.insertOrUpdate(conDriverRideStat);

        ConDriverRideStatDetail conDriverRideStatDetail=new ConDriverRideStatDetail();

        conDriverRideStatDetail.setId(idAppService.getId(SequenceName.CON_DRIVER_RIDE_STAT_DETAIL));
        conDriverRideStatDetail.setDr(0);
        conDriverRideStatDetail.setTenantId(enter.getTenantId());
        conDriverRideStatDetail.setBizId(enter.getBizId());
        conDriverRideStatDetail.setBizType(enter.getBizType());
        conDriverRideStatDetail.setDriverId(enter.getUserId());
        conDriverRideStatDetail.setDuration(enter.getDuration());
        if (conDriverRideStat==null){
            conDriverRideStatDetail.setCo2HistoryTotal(BigDecimal.ZERO);
        }else {
            conDriverRideStatDetail.setCo2HistoryTotal(conDriverRideStat.getCo2Total());
        }
        conDriverRideStatDetail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
        conDriverRideStatDetail.setSvgSpeed(enter.getMileage().divide(new BigDecimal(enter.getDuration())));
        conDriverRideStatDetail.setMileage(enter.getMileage());
        conDriverRideStatDetail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
        conDriverRideStatDetail.setCreateBy(enter.getUserId());
        conDriverRideStatDetail.setCreateTime(new Date());
        conDriverRideStatDetail.setUpdateBy(enter.getUserId());
        conDriverRideStatDetail.setUpdateTime(new Date());
        conDriverRideStatDetailService.insertOrUpdate(conDriverRideStatDetail);
        // 生成骑行详情
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 车辆骑行数据
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/21 11:45
     * @Version: APP 1.2
     */
    @Override
    public GeneralResult saveScooterRideDate(SaveRideDateEnter enter) {
        // 查询车辆骑行数据是否能存在
        QueryWrapper<ConScooterRideStat> conScooterRideStatQueryWrapper=new QueryWrapper<>();
        conScooterRideStatQueryWrapper.eq(ConScooterRideStat.COL_TENANT_ID,enter.getTenantId());
        conScooterRideStatQueryWrapper.eq(ConScooterRideStat.COL_SCOOTER_ID,enter.getBizId());
        ConScooterRideStat conScooterRideStat = conScooterRideStatService.getOne(conScooterRideStatQueryWrapper);
        if (conScooterRideStat==null) {
            // 生成 数据
            conScooterRideStat = new ConScooterRideStat();
            conScooterRideStat.setId(idAppService.getId(SequenceName.CON_SCOOTER_RIDE_STAT));
            conScooterRideStat.setDr(0);
            conScooterRideStat.setTenantId(enter.getTenantId());
            conScooterRideStat.setScooterId(enter.getBizId());
            conScooterRideStat.setTotalDuration(enter.getDuration());
            conScooterRideStat.setCo2Total(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
            conScooterRideStat.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
            conScooterRideStat.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
            conScooterRideStat.setReadTime(null);
            conScooterRideStat.setTotalMileage(enter.getMileage());
            conScooterRideStat.setFirstRideTime(new Date());
            conScooterRideStat.setTotalMileage(enter.getMileage());
            conScooterRideStat.setFirstRideTime(new Date());
            conScooterRideStat.setCreateBy(enter.getUserId());
            conScooterRideStat.setCreateTime(new Date());

        }else{
            // 累加数据
            conScooterRideStat.setTotalDuration(enter.getDuration()+conScooterRideStat.getTotalDuration());
            conScooterRideStat.setCo2Total(conScooterRideStat.getCo2Total().add(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue()))));
            conScooterRideStat.setCo2Increment(conScooterRideStat.getCo2Increment().add(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue()))));
            conScooterRideStat.setSavedMoney(conScooterRideStat.getSavedMoney().add(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue()))));
            BigDecimal svg=(conScooterRideStat.getTotalMileage().add(enter.getMileage())).divide(new BigDecimal(conScooterRideStat.getTotalDuration()+enter.getDuration()));
            conScooterRideStat.setSvgSpeed(svg);
        }
        conScooterRideStat.setUpdateBy(enter.getUserId());
        conScooterRideStat.setUpdateTime(new Date());
        conScooterRideStatService.insertOrUpdate(conScooterRideStat);
        // 生成详情
        ConScooterRideStatDetail conScooterRideStatDetail = new ConScooterRideStatDetail();
        conScooterRideStatDetail.setId(idAppService.getId(SequenceName.CON_SCOOTER_RIDE_STAT_DETAIL));
        conScooterRideStatDetail.setDr(0);
        conScooterRideStatDetail.setTenantId(enter.getTenantId());
        conScooterRideStatDetail.setBizId(enter.getBizId());
        conScooterRideStatDetail.setBizType(enter.getBizType());
        conScooterRideStatDetail.setScooterId(enter.getBizId());
        conScooterRideStatDetail.setDuration(enter.getDuration());
        if (conScooterRideStat==null){
            conScooterRideStatDetail.setCo2HistoryTotal(BigDecimal.ZERO);
        }else {
            conScooterRideStatDetail.setCo2HistoryTotal(conScooterRideStat.getCo2Total());
        }
        conScooterRideStatDetail.setCo2Increment(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(enter.getMileage().longValue())));
        conScooterRideStatDetail.setSvgSpeed(enter.getMileage().divide(new BigDecimal(enter.getDuration())));
        conScooterRideStatDetail.setMileage(enter.getMileage());
        conScooterRideStatDetail.setSavedMoney(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(enter.getMileage().longValue())));
        conScooterRideStatDetail.setCreateBy(enter.getUserId());
        conScooterRideStatDetail.setCreateTime(new Date());
        conScooterRideStatDetail.setUpdateBy(enter.getUserId());
        conScooterRideStatDetail.setUpdateTime(new Date());
        conScooterRideStatDetailService.insertOrUpdate(conScooterRideStatDetail);
        return new GeneralResult(enter.getRequestId());
    }
}
