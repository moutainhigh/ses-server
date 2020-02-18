package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.chart.OrderChartUtils;
import com.redescooter.ses.web.delivery.dao.EdDriverServiceMapper;
import com.redescooter.ses.web.delivery.dao.OrderStatisticsServiceMapper;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.express.EdDriverService;
import com.redescooter.ses.web.delivery.vo.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class EdDriverServiceImpl implements EdDriverService {

    @Autowired
    private EdDriverServiceMapper edDriverServiceMapper;

    @Autowired
    private OrderStatisticsServiceMapper orderStatisticsServiceMapper;

    @Override
    public PageResult<DeliveryHistroyResult> expressOrderHistroy(DeliveryHistroyEnter enter) {
        int count = edDriverServiceMapper.expressOrderHistroy(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<DeliveryHistroyResult> deliveryHistroyList = edDriverServiceMapper.expressOrderHistroyList(enter);
        return PageResult.create(enter, count, deliveryHistroyList);
    }

    /**
     * 快递司机详情单据柱状图
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryChartListResult eDDriverCharts(DeliveryChartEnter enter) {

        if(enter.getId()==0){
            throw new SesWebDeliveryException(ExceptionCodeEnums.ID_IS_EMPTY.getCode(), ExceptionCodeEnums.ID_IS_EMPTY.getMessage());
        }

        enter.setStatus(ExpressOrderStatusEnums.COMPLETED.getValue());
        Map<String, DeliveryChartResult> map = new LinkedHashMap<>();
        List<DeliveryChartResult> deliveryChartResults = new ArrayList<>();
        Double max = 0.00, avg = 0.00, min = 0.00;

        //天数
        int heavens = enter.getHeavens() == 0 ? 1 : enter.getHeavens();
        enter.setHeavens(heavens);
        enter.setDateTimes(enter.getDateTimes() == null ? new Date() : enter.getDateTimes());
        switch (heavens) {
            case 1:
                //今日Today（单位为小时，显示今日配送数据）
                DeliveryChartDto dateTimeParmToday = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParmToday);
                dateTimeParmToday.setDateTime(enter.getDateTimes());
                deliveryChartResults = orderStatisticsServiceMapper.eDDliveryChartToday(dateTimeParmToday);
                break;

            case 7:
                //近七日<7Day（单位为日，显示近7天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
                Date start7 = DateUtil.addDays(enter.getDateTimes(), -7);
                DeliveryChartDto dateTimeParm7 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm7);
                dateTimeParm7.setEndDateTime(enter.getDateTimes());
                dateTimeParm7.setStartDateTime(start7);

                deliveryChartResults = orderStatisticsServiceMapper.eDDeliveryChart7Day(dateTimeParm7);
                break;

            case 30:
                //近30日<30Day（单位为日，显示近30天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
                Date start30 = DateUtil.addDays(enter.getDateTimes(), -30);
                DeliveryChartDto dateTimeParm30 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm30);
                dateTimeParm30.setEndDateTime(enter.getDateTimes());
                dateTimeParm30.setStartDateTime(start30);
                deliveryChartResults = orderStatisticsServiceMapper.eDDeliveryChart30Day(dateTimeParm30);
                break;

            case 365:
                //年Year（单位为月，显示截止至今所有数据，点击某一月可查看该月数据，点击某一日可查看该日数据
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

            //获取最大值
            max = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).max().getAsDouble();
            //获取平均值
            avg = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).average().getAsDouble();
            //取最小值
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
