package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.tool.utils.chart.OrderChartUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.delivery.dao.OrderStatisticsServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.RtDashboardService;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import com.redescooter.ses.web.delivery.vo.DeliveryChartDto;
import com.redescooter.ses.web.delivery.vo.DeliveryChartEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryChartListResult;
import com.redescooter.ses.web.delivery.vo.DeliveryChartResult;
import com.redescooter.ses.web.delivery.vo.MapRsesult;
import com.redescooter.ses.web.delivery.vo.ScooterMapResult;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:OrderStatisticsServiceImpl
 * @description: RtDashboardServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/01/04 12:01
 */
@Service
public class RtDashboardServiceImpl implements RtDashboardService {

    @Autowired
    private OrderStatisticsServiceMapper orderStatisticsServiceMapper;

    @Autowired
    private CorTenantScooterService tenantScooterService;

    @DubboReference
    private TenantBaseService tenantBaseService;

    /**
     * ????????????????????????
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
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<TopTenResult> topTen(TopTenEnter enter) {
        return orderStatisticsServiceMapper.topTen(enter);
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public ScooterRideDataResult scooterRideData(GeneralEnter enter) {
        return orderStatisticsServiceMapper.scooterRideData(enter);
    }

    /**
     * dashboard ??????
     *
     * @param enter
     * @return
     */
    @Override
    public MapRsesult map(GeneralEnter enter) {
        // ??????????????????
        QueryTenantResult tenant = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        // ??????????????????
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

        return MapRsesult.builder()
                .tenantId(tenant.getId())
                .tenantLat(tenant.getLatitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLatitude()))
                .tenantLng(tenant.getLongitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLongitude()))
                .scooterMapResultList(scooterMapResultList)
                .build();
    }

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryChartListResult deliveryChartList(DeliveryChartEnter enter) {
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
                deliveryChartResults = orderStatisticsServiceMapper.deliveryChartToday(dateTimeParmToday);
                break;

            case 7:
                //?????????<7Day???????????????????????????7?????????????????????????????????????????????????????????????????????????????????????????????
                Date start7 = DateUtil.addDays(enter.getDateTimes(), -7);
                DeliveryChartDto dateTimeParm7 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm7);
                dateTimeParm7.setEndDateTime(enter.getDateTimes());
                dateTimeParm7.setStartDateTime(start7);

                deliveryChartResults = orderStatisticsServiceMapper.deliveryChart7Day(dateTimeParm7);
                break;

            case 30:
                //???30???<30Day???????????????????????????30?????????????????????????????????????????????????????????????????????????????????????????????
                Date start30 = DateUtil.addDays(enter.getDateTimes(), -30);
                DeliveryChartDto dateTimeParm30 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm30);
                dateTimeParm30.setEndDateTime(enter.getDateTimes());
                dateTimeParm30.setStartDateTime(start30);
                deliveryChartResults = orderStatisticsServiceMapper.deliveryChart30Day(dateTimeParm30);
                break;

            case 365:
                //???Year??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

    @Override
    public Map<String, Integer> deliveryCountByStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = orderStatisticsServiceMapper.deliveryCountByStatus(enter);
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
}
