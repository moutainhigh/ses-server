package com.redescooter.ses.web.delivery.service.impl;

import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.dao.OrderStatisticsServiceMapper;
import com.redescooter.ses.web.delivery.service.OrderStatisticsService;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
}
