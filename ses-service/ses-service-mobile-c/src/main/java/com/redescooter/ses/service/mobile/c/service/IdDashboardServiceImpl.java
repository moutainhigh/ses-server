package com.redescooter.ses.service.mobile.c.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.c.service.IdDashboardService;
import com.redescooter.ses.api.mobile.c.vo.AllScooterChartResult;
import com.redescooter.ses.api.mobile.c.vo.ScooterChartResult;
import com.redescooter.ses.service.mobile.c.dao.IdDashboardServiceMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import com.redescooter.ses.service.mobile.c.service.base.ConScooterRideStatService;
import com.redescooter.ses.service.mobile.c.service.base.ConUserScooterService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:IdDashboardServiceImpl
 * @description: IdDashboardServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 14:45
 */
@DubboService
@Slf4j
public class IdDashboardServiceImpl implements IdDashboardService {

    @Autowired
    private ConUserScooterService conUserScooterService;

    @Autowired
    private ConScooterRideStatService conScooterRideStatService;

    @Autowired
    private IdDashboardServiceMapper idDashboardServiceMapper;

    /**
     * @param enter
     * @desc: 所有车辆 骑行数据
     * @param: enter
     * @retrn: ScooterChartResult
     * @auther: alex
     * @date: 2020/2/21 14:31
     * @Version: APP 1.2
     */
    @Override
    public ScooterChartResult allScooterChart(GeneralEnter enter) {
        log.info("入参是:[{}]", enter);
        LambdaQueryWrapper<ConUserScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ConUserScooter::getDr, Constant.DR_FALSE);
        qw.eq(ConUserScooter::getUserId, enter.getUserId());
        qw.last("limit 1");
        ConUserScooter userScooter = conUserScooterService.getOne(qw);
        if (null == userScooter) {
            return new ScooterChartResult();
        }

        // 查询骑行数据
        LambdaQueryWrapper<ConScooterRideStat> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ConScooterRideStat::getDr, Constant.DR_FALSE);
        lqw.eq(ConScooterRideStat::getScooterId, userScooter.getScooterId());
        lqw.last("limit 1");
        ConScooterRideStat stat = conScooterRideStatService.getOne(lqw);
        if (null == stat) {
            return new ScooterChartResult();
        }

        ScooterChartResult result = new ScooterChartResult();
        result.setTotalMileage(stat.getTotalMileage().toString());
        result.setTotalCo2(stat.getCo2Total().toString());
        result.setTotalMoney(stat.getSavedMoney().toString());
        result.setAvgSpeed(stat.getSavedMoney().toString());
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * @param enter
     * @desc: 车辆骑行数据
     * @param: enter
     * @retrn: AllScooterChartResult
     * @auther: alex
     * @date: 2020/2/21 14:34
     * @Version: APP 1.2
     */
    @Override
    public AllScooterChartResult scooterChart(DateTimeParmEnter enter) {
        log.info("入参是:[{}]", enter);
        Map<String, ScooterChartResult> map = new LinkedHashMap<>();
        List<String> dayList;
        // 获取指定日期格式向前N天时间集合
//            dayList = DateUtil.getDayList(enter.getDateTime() == null ? new Date() : enter.getDateTime(), 30, null);
        dayList = DateUtil.getBetweenDates(enter.getStartDateTime(), enter.getEndDateTime());
        List<ScooterChartResult> list = idDashboardServiceMapper.scooterChart(enter);
        ScooterChartResult model = null;
        if (CollectionUtils.isEmpty(list)) {
            for (String str : dayList) {
                model = new ScooterChartResult();
                model.setTimes(str);
                map.put(str, model);
            }
        } else {
            for (String str : dayList) {
                for (ScooterChartResult chart : list) {
                    if (chart.getTimes().equals(str)) {
                        map.put(str, chart);
                    }
                }
                if (!map.containsKey(str)) {
                    model = new ScooterChartResult();
                    model.setTimes(str);
                    map.put(str, model);
                }
            }
        }

        AllScooterChartResult result = new AllScooterChartResult();
        result.setAllMap(map);
        result.setRequestId(enter.getRequestId());
        return result;
    }
}
