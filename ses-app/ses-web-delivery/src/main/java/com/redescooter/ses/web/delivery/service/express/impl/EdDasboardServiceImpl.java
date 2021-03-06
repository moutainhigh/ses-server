package com.redescooter.ses.web.delivery.service.express.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.tool.utils.chart.OrderChartUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.delivery.dao.EdDasboardServiceMapper;
import com.redescooter.ses.web.delivery.dao.OrderStatisticsServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import com.redescooter.ses.web.delivery.service.express.EdDasboardService;
import com.redescooter.ses.web.delivery.vo.DeliveryChartDto;
import com.redescooter.ses.web.delivery.vo.DeliveryChartEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryChartListResult;
import com.redescooter.ses.web.delivery.vo.DeliveryChartResult;
import com.redescooter.ses.web.delivery.vo.ScooterMapResult;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;
import com.redescooter.ses.web.delivery.vo.edorder.ExpressOrderMapResult;
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

@Service
public class EdDasboardServiceImpl implements EdDasboardService {

    @Autowired
    private EdDasboardServiceMapper edDasboardServiceMapper;

    @Autowired
    private CorTenantScooterService tenantScooterService;

    @Autowired
    private OrderStatisticsServiceMapper orderStatisticsServiceMapper;

    @DubboReference
    private TenantBaseService tenantBaseService;

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:48
     * @Param: enter
     * @Return: Map
     * @desc: todayCountByStatus
     */
    @Override
    public Map<String, Integer> todayCountByStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = edDasboardServiceMapper.todayCountByStatus(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResultList) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (ExpressOrderStatusEnums status : ExpressOrderStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        map.remove(ExpressOrderStatusEnums.CANCEL.getValue());
        map.remove(ExpressOrderStatusEnums.SHIPPING.getValue());
        return map;
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:49
     * @Param: enter
     * @Return: TopTenResult
     * @desc: ???????????????
     */
    @Override
    public List<TopTenResult> topTen(TopTenEnter enter) {
        return edDasboardServiceMapper.topTen(enter);
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:51
     * @Param: enter
     * @Return: ScooterRideDataResult
     * @desc: ??????????????????
     */
    @Override
    public ScooterRideDataResult scooterRideData(GeneralEnter enter) {
        ScooterRideDataResult result = edDasboardServiceMapper.scooterRideData(enter);
        if (result == null) {
            new ScooterRideDataResult();
        }
        return result;
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:52
     * @Param: enter
     * @Return: ExpressOrderMapResult
     * @desc: ?????????????????????
     */
    @Override
    public ExpressOrderMapResult map(GeneralEnter enter) {
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

        return ExpressOrderMapResult.builder()
                .tenantId(tenant.getId())
                .tenantLat(tenant.getLatitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLatitude()))
                .tenantLng(tenant.getLongitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLongitude()))
                .scooterMapResultList(scooterMapResultList)
                .build();
    }

    /**
     * ??????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryChartListResult eDDeliveryChartList(DeliveryChartEnter enter) {
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

}
