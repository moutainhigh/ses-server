package com.redescooter.ses.web.ros.dao.sales;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sales.CustomerOrderResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderDetailsResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @program: ses-server
 * @description: 销售订单持久层服务
 * @author: Jerry
 * @created: 2020/09/29 22:46
 */
@Mapper
public interface SalesOrderServerMapper {

    /**
     * 销售订单状态统计
     *
     * @return
     */
    List<CountByStatusResult> countStatus();

    /**
     * 销售订单列表总数统计
     *
     * @param enter
     * @return
     */
    int listCount(SalesOrderEnter enter);

    /**
     * 销售订单列表
     *
     * @param enter
     * @return
     */
    List<SalesOrderListResult> list(SalesOrderEnter enter);

    /**
     * 销售订单详情
     *
     * @param enter
     * @return
     */
    SalesOrderDetailsResult details(IdEnter enter);

    /**
     *  查询前一年的数据
     * @param start
     * @param end
     * @return
     */
    List<CustomerOrderResult> orderListByYear(@Param("start") Date start, @Param("end") Date end);

    /**
     * 查询前一月的数据
     * @param start
     * @param end
     * @return
     */
    List<CustomerOrderResult> orderListByMonth(@Param("start") Date start, @Param("end") Date end);

    List<CustomerOrderResult> orderListByDay(@Param("dateTime") Date dateTime);
}
