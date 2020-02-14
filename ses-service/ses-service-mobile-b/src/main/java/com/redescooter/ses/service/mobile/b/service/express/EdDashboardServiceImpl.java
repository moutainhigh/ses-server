package com.redescooter.ses.service.mobile.b.service.express;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.service.express.EdDashboardService;
import com.redescooter.ses.api.mobile.b.vo.MobileBDeliveryChartResult;
import com.redescooter.ses.service.mobile.b.dao.EdDashboardServiceMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class EdDashboardServiceImpl implements EdDashboardService {

    @Autowired
    private EdDashboardServiceMapper edDashboardServiceMapper;

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/14 15:29
     * @Param: enter
     * @Return: map
     * @desc: 所有的订单统计
     * @param enter
     */
    @Override
    public Map<String, Integer> allCountByStatus(GeneralEnter enter) {
        // 所有状态统计 除拒绝订单外
        List<CountByStatusResult> countByStatusResultList=edDashboardServiceMapper.allCountByStatus(enter);
        // 查询拒绝订单
        CountByStatusResult refuseCount=edDashboardServiceMapper.refuseCount(enter);
        return null;
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/14 15:48
     * @Param: enter
     * @Return: MobileBDeliveryChartResult
     * @desc: 订单月统计
     * @param enter
     */
    @Override
    public MobileBDeliveryChartResult mobileBDeliveryChart(DateTimeParmEnter enter) {
        return null;
    }
}
