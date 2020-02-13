package com.redescooter.ses.web.delivery.service.express.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.web.delivery.dao.EdDasboardServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import com.redescooter.ses.web.delivery.service.express.EdDasboardService;
import com.redescooter.ses.web.delivery.vo.ScooterMapResult;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;
import com.redescooter.ses.web.delivery.vo.edorder.ExpressOrderMapResult;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class EdDasboardServiceImpl implements EdDasboardService {
    @Autowired
    private EdDasboardServiceMapper edDasboardServiceMapper;
    @Autowired
    private CorTenantScooterService tenantScooterService;

    @Reference
    private TenantBaseService tenantBaseService;

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:48
     * @Param: enter
     * @Return: Map
     * @desc: todayCountByStatus
     * @param enter
     */
    @Override
    public Map<String, Integer> todayCountByStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = edDasboardServiceMapper.todayCountByStatus(enter);

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
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:49
     * @Param: enter
     * @Return: TopTenResult
     * @desc: 司机排行榜
     * @param enter
     */
    @Override
    public TopTenResult topTen(TopTenEnter enter) {
        return edDasboardServiceMapper.topTen(enter);
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:51
     * @Param: enter
     * @Return: ScooterRideDataResult
     * @desc: 车辆骑行数据
     * @param enter
     */
    @Override
    public ScooterRideDataResult scooterRideData(GeneralEnter enter) {
        return edDasboardServiceMapper.scooterRideData(enter);
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 16:52
     * @Param: enter
     * @Return: ExpressOrderMapResult
     * @desc: 仪表盘地图接口
     * @param enter
     */
    @Override
    public ExpressOrderMapResult map(GeneralEnter enter) {
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

        return ExpressOrderMapResult.builder()
                .tenantId(tenant.getId())
                .tenantLat(tenant.getLatitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLatitude()))
                .tenantLng(tenant.getLongitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLongitude()))
                .scooterMapResultList(scooterMapResultList)
                .build();
    }
}
