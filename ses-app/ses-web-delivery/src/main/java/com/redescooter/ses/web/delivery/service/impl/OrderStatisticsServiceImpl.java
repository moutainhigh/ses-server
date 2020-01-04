package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.web.delivery.dao.OrderStatisticsServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.CorTenantScooterMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.service.OrderStatisticsService;
import com.redescooter.ses.web.delivery.vo.MapResult;
import com.redescooter.ses.web.delivery.vo.ScooterMapResult;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CorTenantScooterMapper corTenantScooterMapper;

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
        map.remove(DeliveryStatusEnums.TIMEOUT_WARNING.getValue());
        map.remove(DeliveryStatusEnums.CHANGED.getValue());
        return map;
    }

    /**
     * 司机排行榜
     *
     * @param enter
     * @return
     */
    @Override
    public TopTenResult topTen(TopTenEnter enter) {
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
        List<CorTenantScooter> corTenantScooterList = corTenantScooterMapper.selectList(corTenantScooterQueryWrapper);

        List<ScooterMapResult> scooterMapResultList = new ArrayList<>();
        corTenantScooterList.forEach(item -> {
            ScooterMapResult scooter = ScooterMapResult.builder()
                    .id(item.getId())
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
}
