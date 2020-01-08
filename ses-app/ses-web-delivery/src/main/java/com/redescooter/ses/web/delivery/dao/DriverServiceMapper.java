package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyResult;
import com.redescooter.ses.web.delivery.vo.DriverScooterHistoryResult;
import com.redescooter.ses.web.delivery.vo.DriverScooterHistroyEnter;
import com.redescooter.ses.web.delivery.vo.ListDriverPage;
import com.redescooter.ses.web.delivery.vo.ListDriverResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 11:28 上午
 * @ClassName: DriverServiceMapper
 * @Function: TODO
 */
public interface DriverServiceMapper {

    /**
     * 司机列表总条数
     *
     * @param page
     * @return
     */
    int listCount(ListDriverPage page);

    /**
     * 车辆列表
     *
     * @param page
     * @return
     */
    List<ListDriverResult> list(ListDriverPage page);

    /**
     * 司机状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countStatus(GeneralEnter enter);

    /**
     * 司机已配送的订单状态
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> driverDeliveryCountByStatus(IdEnter enter);

    /**
     * 司机历史订单
     *
     * @param enter
     * @return
     */
    int deliveryHistroyCount(DeliveryHistroyEnter enter);

    /**
     * 司机历史订单
     *
     * @param enter
     * @return
     */
    List<DeliveryHistroyResult> deliveryHistroyList(DeliveryHistroyEnter enter);

    /**
     * 司机车辆分配表
     *
     * @param enter
     * @return
     */
    int driverscooterHistroyCount(DriverScooterHistroyEnter enter);

    /**
     * 司机车辆分配表
     *
     * @param enter
     * @return
     */
    List<DriverScooterHistoryResult> driverscooterHistroyList(DriverScooterHistroyEnter enter);

    /**
     * 司机派送订单时的骑行距离
     *
     * @param enter
     * @param beginTime
     * @return
     */
    BigDecimal queryScooterMileage(@Param("enter") IdEnter enter, @Param("beginTime") Date beginTime);
}
