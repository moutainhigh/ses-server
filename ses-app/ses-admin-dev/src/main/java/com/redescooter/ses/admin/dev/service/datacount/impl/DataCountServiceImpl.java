package com.redescooter.ses.admin.dev.service.datacount.impl;

import com.redescooter.ses.admin.dev.service.datacount.DataCountService;
import com.redescooter.ses.api.common.vo.count.*;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.chart.OrderChartUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName:DataCountServiceImpl
 * @description: DataCountServiceImpl
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/17 11:55
 */
@Service
public class DataCountServiceImpl implements DataCountService {

    @Reference
    private UserTokenService userTokenService;


    /**
     * 客户统计
     *
     * @param enter
     * @return
     */
    @Override
    public List<CustomerCountResult> customerCount(CustomerCountEnter enter) {
        List<CustomerCountResult> resultList = new ArrayList<>();
        switch (enter.getQueryType()) {
            case 1:
                // 查询注册量
                resultList = registerCount(enter.getType());
            default:
                break;
            case 2:
                // 查询活跃量
                resultList = activeCount(enter.getType());
                break;
        }
        return resultList;
    }


    // 查询注册量 (saas app-ToB app-ToC)
    public List<CustomerCountResult> registerCount(Integer type) {
        List<CustomerCountResult> resultList = new ArrayList<>();
        String dateStr = null;
        switch (type) {
            case 1:
                // 年
                dateStr = DateUtil.format(new Date(), "yyyy");
            default:
                break;
            case 2:
                // 月
                dateStr = DateUtil.format(new Date(), "yyyy-MM");
                break;
            case 3:
                // 日
                dateStr = DateUtil.format(new Date(), "yyyy-MM-dd");
                break;
        }
        // saas
        CustomerCountResult saasUser = new CustomerCountResult();
        List<Integer> saas = new ArrayList<>();
        saas.add(1);
        saas.add(2);
        // 当前时间注册的数量
        saasUser.setCount(userTokenService.registerCount(saas, dateStr));
        // 总的数量
//        saasUser.setTotalCount(userTokenService.totalUserCount(saas));
        saasUser.setCustomerType(1);
        resultList.add(saasUser);

        // app-ToB
        CustomerCountResult toBUser = new CustomerCountResult();
        List<Integer> toB = new ArrayList<>();
        toB.add(1);
        toB.add(2);
        toB.add(3);
        toB.add(4);
        // 当前时间注册的数量
        toBUser.setCount(userTokenService.registerCount(toB, dateStr));
        // 总的数量
//        toBUser.setTotalCount(userTokenService.totalUserCount(toB));
        toBUser.setCustomerType(2);
        resultList.add(toBUser);

        // app-ToC
        CustomerCountResult toCUser = new CustomerCountResult();
        List<Integer> toC = new ArrayList<>();
        toC.add(5);
        // 当前时间注册的数量
        toCUser.setCount(userTokenService.registerCount(toC, dateStr));
        // 总的数量
//        toCUser.setTotalCount(userTokenService.totalUserCount(toC));
        toCUser.setCustomerType(3);
        resultList.add(toCUser);
        return resultList;
    }


    // 查询活跃量 (saas app-ToB app-ToC)
    public List<CustomerCountResult> activeCount(Integer type) {
        List<CustomerCountResult> resultList = new ArrayList<>();
        String dateStr = null;
        switch (type) {
            case 1:
                // 年
                dateStr = DateUtil.format(new Date(), "yyyy");
            default:
                break;
            case 2:
                // 月
                dateStr = DateUtil.format(new Date(), "yyyy-MM");
                break;
            case 3:
                // 日
                dateStr = DateUtil.format(new Date(), "yyyy-MM-dd");
                break;
        }
        // saas
        CustomerCountResult saasUser = new CustomerCountResult();
        List<Integer> saas = new ArrayList<>();
        saas.add(1);
        saas.add(2);
        // 当前时间活跃的数量
        saasUser.setCount(userTokenService.activeCount(saas, dateStr));
        // 总的数量
//        saasUser.setTotalCount(userTokenService.totalUserCount(saas));
        saasUser.setCustomerType(1);
        resultList.add(saasUser);

        // app-ToB
        CustomerCountResult toBUser = new CustomerCountResult();
        List<Integer> toB = new ArrayList<>();
        toB.add(1);
        toB.add(2);
        toB.add(3);
        toB.add(4);
        // 当前时间活跃的数量
        toBUser.setCount(userTokenService.activeCount(toB, dateStr));
        // 总的数量
//        toBUser.setTotalCount(userTokenService.totalUserCount(toB));
        toBUser.setCustomerType(2);
        resultList.add(toBUser);

        // app-ToC
        CustomerCountResult toCUser = new CustomerCountResult();
        List<Integer> toC = new ArrayList<>();
        toC.add(5);
        // 当当前时间活跃的数量
        toCUser.setCount(userTokenService.activeCount(toC, dateStr));
        // 总的数量
//        toCUser.setTotalCount(userTokenService.totalUserCount(toC));
        toCUser.setCustomerType(3);
        resultList.add(toCUser);
        return resultList;
    }


    /**
     * 车辆销售统计
     *
     * @param enter
     * @return
     */
    @Override
    public ScooterCountResult scooterCount(ScooterCountEnter enter) {
        ScooterCountResult result = new ScooterCountResult();
        // 如果没有传  就默认是当年的数据
        enter.setDateTime(enter.getDateTime() == null ? new Date() : enter.getDateTime());
        List<String> dateList = new LinkedList();
        dateList = OrderChartUtils.countDateList(enter.getType(), enter.getDateTime());

        // 构建三个集合 返回三种不同的车型
        List<OrderCountResult> E50s = new ArrayList<>();
        List<OrderCountResult> E100s = new ArrayList<>();
        List<OrderCountResult> E125s = new ArrayList<>();

        // todo 目前还没有这块的业务 先默认写死
        // 如果没有数据 每个月数量默认为0
        for (String dateStr : dateList) {
            OrderCountResult E50 = new OrderCountResult();
            E50.setDateTime(dateStr);
            E50.setTotalCount(100);
            E50s.add(E50);

            OrderCountResult E100 = new OrderCountResult();
            E100.setDateTime(dateStr);
            E100.setTotalCount(150);
            E100s.add(E100);

            OrderCountResult E125 = new OrderCountResult();
            E125.setDateTime(dateStr);
            E125.setTotalCount(200);
            E125s.add(E125);
        }
        result.setScooterE50s(E50s);
        result.setScooterE100s(E100s);
        result.setScooterE125s(E125s);
        return result;
    }
}
