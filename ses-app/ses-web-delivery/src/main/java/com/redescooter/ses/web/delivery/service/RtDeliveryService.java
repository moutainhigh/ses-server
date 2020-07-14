package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdsEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 3:40 下午
 * @ClassName: RtDeliveryService
 * @Function: TODO
 */
public interface RtDeliveryService {

    /**
     * 创建配送单
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveOrderDeliveryEnter enter);

    /**
     * 配送单状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);


    /**
     * 订单分页查询
     *
     * @param page
     * @return
     */
    PageResult<ListDeliveryResult> list(ListDeliveryPage page);

    /**
     * 配送单详情
     *
     * @param enter
     * @return
     */
    DeliveryDetailsResult details(IdEnter enter);

    /**
     * @param enter
     * @return
     */
    List<SelectDriverResult> selectDriverList(IdsEnter enter);

    /**
     * 关闭订单
     *
     * @param enter
     * @return
     */
    GeneralResult closed(ClosedEnter enter);

    /**
     * 订单地图
     *
     * @param enter
     * @return
     */
    MapRsesult map(MapEnter enter);

    /**
     * 车牌号列表
     *
     * @param enter
     * @return
     */
    List<ScooterLicensePlateResult> scooterLicensePlate(ScooterLicensePlateEnter enter);

    /**
     * 订单信息
     *
     * @param enter
     * @return
     */
    DriverDeliveryInfoResult driverDeliveryInfor(IdEnter enter);

    /**
     * 车辆信息
     *
     * @param enter
     * @return
     */
    ScooterMapResult scooterInfor(IdEnter enter);

    /**
     * 订单节点
     *
     * @param enter
     * @return
     */
    List<DeliveryNodeResult> nodeList(IdEnter enter);

    /**
     * 订单重新分配
     *
     * @param enter
     * @return
     */
    GeneralResult deliveryReset(DeliveryResetEnter enter);
}
