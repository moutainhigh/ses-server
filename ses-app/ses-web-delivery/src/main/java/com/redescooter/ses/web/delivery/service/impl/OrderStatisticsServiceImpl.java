package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.delivery.dao.OrderStatisticsServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.OrderStatisticsService;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import com.redescooter.ses.web.delivery.vo.*;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @ClassName:OrderStatisticsServiceImpl
 * @description: OrderStatisticsServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 12:01
 */
@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    @Autowired
    private OrderStatisticsServiceMapper orderStatisticsServiceMapper;

    @Autowired
    private CorTenantScooterService tenantScooterService;

    @Reference
    private TenantBaseService tenantBaseService;

    /**
     * 今日订单状态统计
     *
     * @param enter
     * @return
     */
    @Override

    public Map<String, Integer> todayCountByStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = orderStatisticsServiceMapper.todayCountByStatus(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResultList) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (DeliveryStatusEnums status : DeliveryStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 司机排行榜
     *
     * @param enter
     * @return
     */
    @Override
    public List<TopTenResult> topTen(TopTenEnter enter) {
        return orderStatisticsServiceMapper.topTen(enter);
    }

    /**
     * 车辆骑行数据
     *
     * @param enter
     * @return
     */
    @Override
    public ScooterRideDataResult scooterRideData(GeneralEnter enter) {
        return orderStatisticsServiceMapper.scooterRideData(enter);
    }

    /**
     * dashboard 地图
     *
     * @param enter
     * @return
     */
    @Override
    public MapResult map(GeneralEnter enter) {
        // 查询门店信息
        QueryTenantResult tenant = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        // 查询车辆信息
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
        List<CorTenantScooter> corTenantScooterList = tenantScooterService.list(corTenantScooterQueryWrapper);

        List<ScooterMapResult> scooterMapResultList = new ArrayList<>();
        corTenantScooterList.forEach(item -> {
            ScooterMapResult scooter = ScooterMapResult.builder()
                    .id(item.getScooterId())
                    .lat(item.getLatitude().toString())
                    .lng(item.getLongitule().toString())
                    .build();
            scooterMapResultList.add(scooter);
        });
        return MapResult.builder()
                .tenantId(tenant.getId())
                .tenantLat(tenant.getLatitude().toString())
                .tenantLng(tenant.getLongitude().toString())
                .scooterMapResultList(scooterMapResultList)
                .build();
    }

    /**
     * 仪表盘订单柱状图
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryChartListResult deliveryChartList(DeliveryChartEnter enter) {
        Map<String, DeliveryChartResult> map = new HashMap<>();
        List<DeliveryChartResult> deliveryChartResults = new ArrayList<>();
        //天数
        int heaven = enter.getHeaven();
        enter.setDateTimes(enter.getDateTimes() == null ? new Date() : enter.getDateTimes());
        switch (heaven) {
            case 1:
                //今日Today（单位为小时，显示今日配送数据）
                DeliveryChartDto dateTimeParmToday = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParmToday);
                dateTimeParmToday.setEndDateTime(enter.getDateTimes());
                deliveryChartResults = orderStatisticsServiceMapper.deliveryChartToday(dateTimeParmToday);
                break;

            case 7:
                //近七日<7Day（单位为日，显示近7天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
                Date start7 = DateUtil.addDays(enter.getDateTimes(), -7);
                DeliveryChartDto dateTimeParm7 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm7);
                dateTimeParm7.setEndDateTime(enter.getDateTimes());
                dateTimeParm7.setStartDateTime(start7);

                deliveryChartResults = orderStatisticsServiceMapper.deliveryChart7Day(dateTimeParm7);
                break;

            case 30:
                //近30日<30Day（单位为日，显示近30天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
                Date start30 = DateUtil.addDays(enter.getDateTimes(), -30);
                DeliveryChartDto dateTimeParm30 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm30);
                dateTimeParm30.setEndDateTime(enter.getDateTimes());
                dateTimeParm30.setStartDateTime(start30);
                deliveryChartResults = orderStatisticsServiceMapper.deliveryChart30Day(dateTimeParm30);
                break;

            case 365:
                //年Year（单位为月，显示截止至今所有数据，点击某一月可查看该月数据，点击某一日可查看该日数据
                Date start365 = DateUtil.addDays(enter.getDateTimes(), -365);
                DeliveryChartDto dateTimeParm365 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm365);
                dateTimeParm365.setEndDateTime(enter.getDateTimes());
                dateTimeParm365.setStartDateTime(start365);
                deliveryChartResults = orderStatisticsServiceMapper.deliveryChart365Day(dateTimeParm365);
                break;

            default:
                throw new SesWebDeliveryException(ExceptionCodeEnums.OPERATION_ERROR.getCode(), ExceptionCodeEnums.OPERATION_ERROR.getMessage());

        }

        List<String> dateList = getDateList(enter.getHeaven(), enter.getDateTimes());

        if (deliveryChartResults.size() > 0) {
            for (String str : dateList) {
                for (DeliveryChartResult chart : deliveryChartResults) {
                    if (chart.getTimes().equals(str)) {
                        map.put(str, chart);
                        continue;
                    }
                }
            }

            for (String str : dateList) {
                if (!map.containsKey(str)) {
                    DeliveryChartResult result = new DeliveryChartResult();
                    result.setTimes(str);
                    map.put(str, result);
                }
            }

        } else {
            for (String time : dateList) {
                DeliveryChartResult result = new DeliveryChartResult();
                result.setTimes(time);
                map.put(time, result);
            }
        }

        return new DeliveryChartListResult(map);
    }

    private List<String> getDateList(int heaven, Date date) {
        ArrayList<String> list = new ArrayList<>();
        switch (heaven) {
            case 1:
                list = DateUtil.get24HourList(DateUtil.getDateTimeStamp(date));
                break;
            case 7:
                list = DateUtil.getDayList(date, 7, null);
                break;
            case 30:
                list = DateUtil.getDayList(date, 30, null);
                break;
            case 365:
                list = DateUtil.getDayList(date, 365, DateUtil.DEFAULT_YYMM_FORMAT);
                break;
        }

        return checkDayResultSingle(list);
    }

    //去除重复的时间，只供柱状图使用。
    private ArrayList<String> checkDayResultSingle(ArrayList<String> dayList) {
        ArrayList<String> temp = new ArrayList<String>();
        Iterator<String> iterator = dayList.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (!temp.contains(str)) {
                temp.add(str);
            }
        }
        return temp;
    }
}
