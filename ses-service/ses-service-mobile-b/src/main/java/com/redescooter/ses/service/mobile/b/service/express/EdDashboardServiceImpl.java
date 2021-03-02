package com.redescooter.ses.service.mobile.b.service.express;

import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.service.express.EdDashboardService;
import com.redescooter.ses.api.mobile.b.vo.express.EdMobileBExpressOrderChartResult;
import com.redescooter.ses.api.mobile.b.vo.express.EdMonthlyExpressOrderChartResult;
import com.redescooter.ses.service.mobile.b.dao.EdDashboardServiceMapper;
import com.redescooter.ses.tool.utils.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@DubboService
public class EdDashboardServiceImpl implements EdDashboardService {

    @Autowired
    private EdDashboardServiceMapper edDashboardServiceMapper;

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/14 15:29
     * @Param: enter
     * @Return: map
     * @desc: 所有的订单统计
     */
    @Override
    public Map<String, Integer> allCountByStatus(GeneralEnter enter) {
        // 所有状态统计 除拒绝订单外
        List<CountByStatusResult> countByStatusResultList = edDashboardServiceMapper.allCountByStatus(enter);
        // 查询拒绝订单
        CountByStatusResult refuseCount = edDashboardServiceMapper.refuseCount(enter);

        Map<String, Integer> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(countByStatusResultList)) {
            for (CountByStatusResult item : countByStatusResultList) {
                map.put(item.getStatus(), item.getTotalCount());
            }
        }
        for (ExpressOrderStatusEnums status : ExpressOrderStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        if (refuseCount != null) {
            map.put(refuseCount.getStatus(), refuseCount.getTotalCount());
        }
        return map;
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/14 15:48
     * @Param: enter
     * @Return: MobileBDeliveryChartResult
     * @desc: 订单月统计
     */
    @Override
    public EdMobileBExpressOrderChartResult mobileBExpressOrderChart(DateTimeParmEnter enter) {
        Map<String, EdMonthlyExpressOrderChartResult> allMap = new LinkedHashMap<>();
        Map<String, EdMonthlyExpressOrderChartResult> listMap = new LinkedHashMap<>();

        List<EdMonthlyExpressOrderChartResult> list = edDashboardServiceMapper.mobileBDeliveryChart(enter);

        List<String> dayList = new LinkedList();
        // 获取指定日期格式向前N天时间集合
//        dayList = DateUtil.getDayList(enter.getDateTime() == null ? new Date() : enter.getDateTime(), 30, null);
        dayList = DateUtil.getBetweenDates(enter.getStartDateTime(), enter.getEndDateTime());

        if (list.size() == 0) {
            EdMobileBExpressOrderChartResult edMobileBExpressOrderChartResult = new EdMobileBExpressOrderChartResult();
            dayList.forEach(item -> {
                EdMonthlyExpressOrderChartResult edMonthlyExpressOrderChartResult = new EdMonthlyExpressOrderChartResult();
                edMonthlyExpressOrderChartResult.setTimes(item);
                allMap.put(item, edMonthlyExpressOrderChartResult);
            });
            edMobileBExpressOrderChartResult.setAllMap(allMap);
            return edMobileBExpressOrderChartResult;
        } else {
            EdMonthlyExpressOrderChartResult result = null;
            for (String str : dayList) {
                for (EdMonthlyExpressOrderChartResult chart : list) {
                    if (chart.getTimes().equals(str)) {
                        allMap.put(str, chart);
                        listMap.put(str, chart);
                    }
                }
                if (!allMap.containsKey(str)) {
                    result = new EdMonthlyExpressOrderChartResult();
                    result.setTimes(str);
                    allMap.put(str, result);
                }
            }
        }

        EdMobileBExpressOrderChartResult result = new EdMobileBExpressOrderChartResult();
        result.setAllMap(allMap);
        result.setListMap(listMap);
        result.setRequestId(enter.getRequestId());
        return result;
    }
}
