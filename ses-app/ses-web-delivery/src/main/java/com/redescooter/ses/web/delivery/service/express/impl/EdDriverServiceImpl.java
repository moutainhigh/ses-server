package com.redescooter.ses.web.delivery.service.express.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.driver.DriverLicenseLevelEnum;
import com.redescooter.ses.api.common.enums.driver.DriverStatusEnum;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.tenant.TenantScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.chart.OrderChartUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.EdDriverServiceMapper;
import com.redescooter.ses.web.delivery.dao.OrderStatisticsServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import com.redescooter.ses.web.delivery.dm.CorDriverScooterHistory;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.base.CorDriverScooterHistoryService;
import com.redescooter.ses.web.delivery.service.base.CorDriverScooterService;
import com.redescooter.ses.web.delivery.service.base.CorDriverService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryService;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import com.redescooter.ses.web.delivery.service.express.EdDriverService;
import com.redescooter.ses.web.delivery.vo.AssignScooterEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryChartDto;
import com.redescooter.ses.web.delivery.vo.DeliveryChartEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryChartListResult;
import com.redescooter.ses.web.delivery.vo.DeliveryChartResult;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class EdDriverServiceImpl implements EdDriverService {

    @Autowired
    private EdDriverServiceMapper edDriverServiceMapper;

    @Autowired
    private OrderStatisticsServiceMapper orderStatisticsServiceMapper;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private CorDriverService driverService;

    @Autowired
    private CorDriverScooterService driverScooterService;

    @Autowired
    private CorDriverScooterHistoryService driverScooterHistoryService;

    @Autowired
    private CorTenantScooterService tenantScooterService;

    @Autowired
    private CorExpressDeliveryService corExpressDeliveryService;

    @Override
    public PageResult<DeliveryHistroyResult> expressOrderHistroy(DeliveryHistroyEnter enter) {
        int count = edDriverServiceMapper.expressOrderHistroy(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<DeliveryHistroyResult> deliveryHistroyList = edDriverServiceMapper.expressOrderHistroyList(enter);
        return PageResult.create(enter, count, deliveryHistroyList);
    }

    /**
     * ?????????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryChartListResult eDDriverCharts(DeliveryChartEnter enter) {

        if(enter.getId()==0){
            throw new SesWebDeliveryException(ExceptionCodeEnums.ID_IS_EMPTY.getCode(), ExceptionCodeEnums.ID_IS_EMPTY.getMessage());
        }

        enter.setStatus(ExpressOrderStatusEnums.COMPLETED.getValue());
        Map<String, DeliveryChartResult> map = new LinkedHashMap<>();
        List<DeliveryChartResult> deliveryChartResults = new ArrayList<>();
        Double max = 0.00, avg = 0.00, min = 0.00;

        //??????
        int heavens = enter.getHeavens() == 0 ? 1 : enter.getHeavens();
        enter.setHeavens(heavens);
        enter.setDateTimes(enter.getDateTimes() == null ? new Date() : enter.getDateTimes());
        switch (heavens) {
            case 1:
                //??????Today????????????????????????????????????????????????
                DeliveryChartDto dateTimeParmToday = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParmToday);
                dateTimeParmToday.setDateTime(enter.getDateTimes());
                deliveryChartResults = orderStatisticsServiceMapper.eDDliveryChartToday(dateTimeParmToday);
                break;
            case 7:
                //?????????<7Day???????????????????????????7?????????????????????????????????????????????????????????????????????????????????????????????
                Date start7 = DateUtil.addDays(enter.getDateTimes(), -7);
                DeliveryChartDto dateTimeParm7 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm7);
                dateTimeParm7.setEndDateTime(enter.getDateTimes());
                dateTimeParm7.setStartDateTime(start7);

                deliveryChartResults = orderStatisticsServiceMapper.eDDeliveryChart7Day(dateTimeParm7);
                break;
            case 30:
                //???30???<30Day???????????????????????????30?????????????????????????????????????????????????????????????????????????????????????????????
                Date start30 = DateUtil.addDays(enter.getDateTimes(), -30);
                DeliveryChartDto dateTimeParm30 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm30);
                dateTimeParm30.setEndDateTime(enter.getDateTimes());
                dateTimeParm30.setStartDateTime(start30);
                deliveryChartResults = orderStatisticsServiceMapper.eDDeliveryChart30Day(dateTimeParm30);
                break;
            case 365:
                //???Year??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                Date start365 = DateUtil.addDays(enter.getDateTimes(), -365);
                DeliveryChartDto dateTimeParm365 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm365);
                dateTimeParm365.setEndDateTime(enter.getDateTimes());
                dateTimeParm365.setStartDateTime(start365);
                deliveryChartResults = orderStatisticsServiceMapper.eDDeliveryChart365Day(dateTimeParm365);
                break;
            default:
                throw new SesWebDeliveryException(ExceptionCodeEnums.OPERATION_ERROR.getCode(), ExceptionCodeEnums.OPERATION_ERROR.getMessage());
        }

        List<String> dateList = new LinkedList();
        dateList = OrderChartUtils.getDateList(heavens, enter.getDateTimes());

        if (deliveryChartResults.size() > 0) {

            //???????????????
            max = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).max().getAsDouble();
            //???????????????
            avg = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).average().getAsDouble();
            //????????????
            min = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).min().getAsDouble();

            DeliveryChartResult result = null;

            for (String str : dateList) {
                for (DeliveryChartResult chart : deliveryChartResults) {
                    if (chart.getTimes().equals(str)) {
                        map.put(str, chart);
                    }
                }
                if (!map.containsKey(str)) {
                    result = new DeliveryChartResult();
                    result.setTimes(str);
                    map.put(str, result);
                }
            }
        } else {
            map = null;
        }
        DeliveryChartListResult result = new DeliveryChartListResult();
        result.setMap(map);
        result.setAvg(avg);
        result.setMax(max);
        result.setMin(min);
        return result;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult assignScooter(AssignScooterEnter enter) {

//        RedisLock.getInstance(jedisCluster).lock(String.valueOf(enter.getScooterId()));
//        try {
        CorDriver driver = driverService.getById(enter.getDriverId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(driver.getStatus(), DriverStatusEnum.DEPARTURE.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_DEPARTURE.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_DEPARTURE.getMessage());
        }
        if (StringUtils.equals(driver.getStatus(), DriverStatusEnum.WORKING.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getMessage());
        }
        if (!driver.getDriverLicenseLevel().equals(DriverLicenseLevelEnum.HIGH.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.NO_DRIVER_LICENSE.getCode(), ExceptionCodeEnums.NO_DRIVER_LICENSE.getMessage());
        }

        QueryWrapper<CorDriverScooter> driverScooterQueryWrapper = new QueryWrapper<>();
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getDriverId());
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
        CorDriverScooter driverScooterOne = driverScooterService.getOne(driverScooterQueryWrapper);

        if (driverScooterOne == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        // ????????????
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
        CorDriverScooter driverScooter = driverScooterService.getOne(driverScooterQueryWrapper);
        if (driverScooter != null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getCode(), ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getMessage());
        }

        //?????????????????????????????????????????????
        driverScooterOne.setStatus(DriverScooterStatusEnums.USED.getValue());
        driverScooterOne.setScooterId(enter.getScooterId());
        driverScooterOne.setBeginTime(new Date());
        driverScooterOne.setEndTime(null);
        driverScooterOne.setUpdatedBy(enter.getDriverId());
        driverScooterOne.setUpdatedTime(new Date());
        driverScooterService.updateById(driverScooterOne);

        //????????????????????????
        CorDriverScooterHistory driverScooterHistory = new CorDriverScooterHistory();
        driverScooterHistory.setId(idAppService.getId(SequenceName.COR_DRIVER_SCOOTER));
        driverScooterHistory.setDr(0);
        driverScooterHistory.setTenantId(enter.getTenantId());
        driverScooterHistory.setDriverId(driver.getId());
        driverScooterHistory.setScooterId(driverScooterOne.getScooterId());
        driverScooterHistory.setBeginTime(driverScooterOne.getBeginTime());
        driverScooterHistory.setStatus(driverScooterOne.getStatus());
        driverScooterHistory.setCreatedBy(enter.getUserId());
        driverScooterHistory.setCreatedTime(new Date());
        driverScooterHistory.setUpdatedBy(enter.getUserId());
        driverScooterHistory.setUpdatedTime(new Date());
        driverScooterHistoryService.save(driverScooterHistory);

        QueryWrapper<CorTenantScooter> scooterQueryWrapper = new QueryWrapper<>();
        scooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
        scooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        scooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, enter.getScooterId());
        CorTenantScooter tenantScooter = tenantScooterService.getOne(scooterQueryWrapper);
        if (tenantScooter == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        // 125CC ????????????
        if (StringUtils.equals(tenantScooter.getModel(), ScooterModelEnums.SCOOTER_125_CC.getValue())) {
            if (!driver.getDriverLicenseLevel().equals(DriverLicenseLevelEnum.HIGH.getValue())) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.NO_DRIVER_LICENSE.getCode(), ExceptionCodeEnums.NO_DRIVER_LICENSE.getMessage());
            }
        }
        tenantScooter.setStatus(TenantScooterStatusEnums.USEING.getValue());
        tenantScooter.setUpdatedBy(enter.getUserId());
        tenantScooter.setUpdatedTime(new Date());
        tenantScooterService.updateById(tenantScooter);
        //??????????????????
        driver.setStatus(DriverStatusEnum.WORKING.getValue());
        driver.setUpdatedTime(new Date());
        driver.setUpdatedBy(enter.getUserId());
        driverService.updateById(driver);

//        } catch (SesWebDeliveryException e) {
//            RedisLock.getInstance(jedisCluster).unlock(String.valueOf(enter.getScooterId()));
//            throw new SesWebDeliveryException(ExceptionCodeEnums.NON_REPEATABLE.getCode(), ExceptionCodeEnums.NON_REPEATABLE.getMessage());
//        } finally {
//            RedisLock.getInstance(jedisCluster).unlock(String.valueOf(enter.getScooterId()));
//        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult removeScooter(IdEnter enter) {
        CorDriver driver = driverService.getById(enter.getId());
        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        //??????????????????????????????????????????
        int count = edDriverServiceMapper.queryTodayDriverUncompletedOrder(enter);
        if (count != 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.YOU_HAVE_AN_ORDER_IN_PROGRESS.getCode(), ExceptionCodeEnums.YOU_HAVE_AN_ORDER_IN_PROGRESS.getMessage());
        }
        //??????????????????????????????????????????
        QueryWrapper<CorDriverScooter> driverScooterQueryWrapper = new QueryWrapper<>();
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getId());
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
        driverScooterQueryWrapper.last("LIMIT 1");
        CorDriverScooter driverScooterOne = driverScooterService.getOne(driverScooterQueryWrapper);

        // ??????????????????
        BigDecimal mileage = edDriverServiceMapper.queryScooterMileage(enter);
        if (null == mileage) {
            mileage = BigDecimal.ZERO;
        }

        // ?????????????????????????????????
        //????????????????????????
        CorDriverScooterHistory driverScooterHistory = new CorDriverScooterHistory();
        driverScooterHistory.setId(idAppService.getId(SequenceName.COR_DRIVER_SCOOTER));
        driverScooterHistory.setDr(0);
        driverScooterHistory.setStatus(DriverScooterStatusEnums.FINSH.getValue());
        driverScooterHistory.setTenantId(enter.getTenantId());
        driverScooterHistory.setDriverId(driver.getId());
        driverScooterHistory.setScooterId(driverScooterOne.getScooterId());
        driverScooterHistory.setBeginTime(driverScooterOne.getBeginTime());
        driverScooterHistory.setEndTime(new Date());
        driverScooterHistory.setMileage(mileage.toString());
        driverScooterHistory.setCreatedBy(enter.getUserId());
        driverScooterHistory.setCreatedTime(new Date());
        driverScooterHistory.setUpdatedBy(enter.getId());
        driverScooterHistory.setUpdatedTime(new Date());
        driverScooterHistoryService.save(driverScooterHistory);

        QueryWrapper<CorTenantScooter> tenantScooterQueryWrapper = new QueryWrapper<>();
        tenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, driverScooterOne.getScooterId());
        tenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        tenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);

        CorTenantScooter updateTenantScooter = new CorTenantScooter();
        updateTenantScooter.setStatus(TenantScooterStatusEnums.AVAILABLE.getValue());
        updateTenantScooter.setUpdatedBy(enter.getUserId());
        updateTenantScooter.setUpdatedTime(new Date());
        tenantScooterService.update(updateTenantScooter, tenantScooterQueryWrapper);

        driver.setStatus(DriverStatusEnum.OFFWORK.getValue());
        driver.setUpdatedBy(enter.getUserId());
        driver.setUpdatedTime(new Date());
        driverService.updateById(driver);

        driverScooterOne.setStatus(DriverScooterStatusEnums.FINSH.getValue());
        driverScooterOne.setScooterId(new Long("0"));
        driverScooterOne.setEndTime(null);
        driverScooterOne.setBeginTime(null);
        driverScooterOne.setUpdatedBy(enter.getUserId());
        driverScooterOne.setUpdatedTime(new Date());
        driverScooterService.updateById(driverScooterOne);
        return new GeneralResult(enter.getRequestId());
    }

}
