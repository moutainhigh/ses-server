package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.*;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 4:52 下午
 * @ClassName: OrderDeliveryServiceMapper
 * @Function: TODO
 */
public interface OrderDeliveryServiceMapper {
    /**
     * 配送单状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countStatus(GeneralEnter enter);


    /**
     * 配送单总数
     *
     * @return
     */
    int listCount(ListDeliveryPage page);

    /**
     * 配送单分页列表
     *
     * @return
     */
    List<ListDeliveryResult> list(ListDeliveryPage page);

    /**
     * 订单详情
     *
     * @param enter
     * @return
     */
    DeliveryDetailsResult details(IdEnter enter);

    List<SelectDriverResult> selectDriverList(GeneralEnter enter);

    /**
     * 车辆地图统计
     *
     * @param enter
     * @return
     */
    List<ScooterMapResult> scooterMap(MapEnter enter);

    /**
     * 司机订单信息
     *
     * @param enter
     * @return
     */
    DriverOrderInfoResult driverDeliveryInfor(IdEnter enter);

    /**
     * 订单 节点
     *
     * @param enter
     * @return
     */
    List<DeliveryNodeResult> nodeList(IdEnter enter);

    /**
     * 门店车牌号列表
     *
     * @param enter
     * @return
     */
    List<ScooterLicensePlateResult> scooterLicensePlateList(ScooterLicensePlateEnter enter);

    /**
     * 司机信息
     *
     * @param enter
     * @return
     */
    ScooterMapResult driverInfo(IdEnter enter);
}
