package com.redescooter.ses.service.mobile.c.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.redescooter.ses.tool.utils.DateUtil;

/**
 * @ClassName:IdDashboardServiceImpl
 * @description: IdDashboardServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 14:45
 */
@Service
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
        QueryWrapper<ConUserScooter> conUserScooterQueryWrapper=new QueryWrapper();
        conUserScooterQueryWrapper.eq(ConUserScooter.COL_DR,0);
        conUserScooterQueryWrapper.eq(ConUserScooter.COL_USER_ID,enter.getUserId());
        ConUserScooter conUserScooter = conUserScooterService.getOne(conUserScooterQueryWrapper);
        if (conUserScooter == null) {
            return new ScooterChartResult();
        }
        // 查询骑行数据
        QueryWrapper<ConScooterRideStat> conScooterRideStatQueryWrapper=new QueryWrapper<>();
        conScooterRideStatQueryWrapper.eq(ConScooterRideStat.COL_DR,0);
        conScooterRideStatQueryWrapper.eq(ConScooterRideStat.COL_SCOOTER_ID,conUserScooter.getScooterId());
        ConScooterRideStat conScooterRideStat = conScooterRideStatService.getOne(conScooterRideStatQueryWrapper);
        if (conScooterRideStat == null) {
            return new ScooterChartResult();
        }
        return ScooterChartResult.builder()
                .avgSpeed(conScooterRideStat.getSavedMoney().toString())
                .totalCo2(conScooterRideStat.getCo2Total().toString())
                .totalMileage(conScooterRideStat.getTotalMileage().toString())
                .totalMoney(conScooterRideStat.getSavedMoney().toString())
                .build();
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

        Map<String, ScooterChartResult> allMap = new LinkedHashMap<>();

        List<ScooterChartResult> list = idDashboardServiceMapper.scooterChart(enter);

        if (list.size() == 0) {
            return new AllScooterChartResult();
        } else {
            ScooterChartResult result = null;
            List<String> dayList = new LinkedList();
            // 获取指定日期格式向前N天时间集合
            dayList = DateUtil.getDayList(enter.getDateTime() == null ? new Date() : enter.getDateTime(), 30, null);

            for (String str : dayList) {
                for (ScooterChartResult chart : list) {
                    if (chart.getTimes().equals(str)) {
                        allMap.put(str, chart);
                    }
                }
                if (!allMap.containsKey(str)) {
                    result = new ScooterChartResult();
                    result.setTimes(str);
                    allMap.put(str, result);
                }
            }
        }

        AllScooterChartResult result = new AllScooterChartResult();
        result.setAllMap(allMap);
        result.setRequestId(enter.getRequestId());
        return result;
    }
}
