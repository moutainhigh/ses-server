package com.redescooter.ses.web.ros.service.ordercount;

import com.redescooter.ses.api.common.service.OrderCountService;
import com.redescooter.ses.api.common.vo.count.OrderCountEnter;
import com.redescooter.ses.api.common.vo.count.OrderCountResult;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.tool.utils.chart.OrderChartUtils;
import com.redescooter.ses.web.ros.dao.sales.SalesOrderServerMapper;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.vo.sales.CustomerOrderResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName:OrderCountServiceImpl
 * @description: OrderCountServiceImpl
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/16 17:09
 */
@Service
public class OrderCountServiceImpl implements OrderCountService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private SalesOrderServerMapper salesOrderServerMapper;


    @Override
    public List<OrderCountResult> orderCount(OrderCountEnter enter) {
        // 先判断本次查询的是年，月，日
        // 暂时先默认查询的是年，月，日
        List<OrderCountResult> resultList = new LinkedList();
        // 如果没有传  就默认是当年的数据
        enter.setDateTime(enter.getDateTime() == null?new Date():enter.getDateTime());
        List<String> dateList = new LinkedList();
        dateList = OrderChartUtils.countDateList(enter.getType(), enter.getDateTime());

        List<CustomerOrderResult> orderResults = null;
        switch (enter.getType()){
            case 1:
                // 查询当年每个月的数据
                // 先查出当年所有的数据
                Date startTime = DateUtil.addDays(enter.getDateTime(), -365);
                orderResults = salesOrderServerMapper.orderListByYear(startTime,enter.getDateTime());
            default:
                break;
            case 2:
                // 查询当月每天的数据
                Date startMonthTime = DateUtil.addDays(enter.getDateTime(), -30);
                orderResults = salesOrderServerMapper.orderListByMonth(startMonthTime,enter.getDateTime());
                break;
            case 3:
                // 查询当天的数据
                orderResults = salesOrderServerMapper.orderListByDay(enter.getDateTime());
                break;

        }
        if (!CollectionUtils.isEmpty(orderResults)){
            for (String dateStr : dateList) {
                OrderCountResult orderCount = new OrderCountResult();
                orderCount.setDateTime(dateStr);
                for (CustomerOrderResult order : orderResults){
                    // 循环查出来的询价单  看是否有满足条件的数据
                    if (order.getTimes().equals(dateStr)){
                        orderCount.setTotalCount(order.getTotal());
                        break;
                    }
                }
                resultList.add(orderCount);
            }
        }else {
            // 如果没有数据 每个月数量默认为0
            for (String dateStr : dateList) {
                OrderCountResult orderCount = new OrderCountResult();
                orderCount.setDateTime(dateStr);
                orderCount.setTotalCount(0);
                resultList.add(orderCount);
            }
        }
        return resultList;
    }
}
