package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 6:54 上午
 * @ClassName: RtDriverService
 * @Function: TODO
 */
public interface RtDriverService {

    /**
     * 创建司机
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveDriverEnter enter);

    /**
     * 2B司机账号开通
     *
     * @param enter
     * @return
     */
    BaseUserResult openDriver2BAccout(SaveDriverEnter enter);

    /**
     * 司机状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 司机分页列表
     *
     * @param page
     * @return
     */
    PageResult<ListDriverResult> list(ListDriverPage page);

    /**
     * 司机详情
     *
     * @param enter
     * @return
     */
    DriverDetailsResult details(IdEnter enter);


    /**
     * 司机离职
     *
     * @param enter
     * @return
     */
    GeneralResult leave(IdEnter enter);

    /**
     * 再次发生激活邮件2B
     *
     * @param enter
     * @return
     */
    GeneralResult againSendEmail(IdEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/13 10:23
    * @Param:  enter
    * @Return: map
    * @desc: 车辆类型
    */
    Map<String, Integer> scooterTypeList(GeneralEnter enter);

    /**
     * 门店车辆列表
     *
     * @param enter
     * @return
     */
    List<ListScooterResult> scooterList(GeneralEnter enter);

    /**
     * 司机车辆分配
     *
     * @param enter
     * @return
     */
    GeneralResult assignScooter(AssignScooterEnter enter);

    /**
     * 司机归还车辆
     *
     * @param enter
     * @return
     */
    GeneralResult removeScooter(IdEnter enter);

    /**
     * 司机已配送订单状态统计
     *
     * @return
     */
    Map<String, Integer> driverDeliveryCountByStatus(IdEnter enter);

    /**
     * 车辆 信息
     *
     * @param enter
     * @return
     */
    DriverScooterInforResult driverScooterInfor(IdEnter enter);

    /**
     * 订单历史
     *
     * @param enter
     * @return
     */
    PageResult<DeliveryHistroyResult> deliveryHistroy(DeliveryHistroyEnter enter);

    /**
     * 司机车辆骑行分配记录
     *
     * @param enter
     * @return
     */
    PageResult<DriverScooterHistoryResult> driverscooterHistroy(DriverScooterHistroyEnter enter);

    /**
     * 司机仪表盘订单柱状图
     *
     * @param enter
     * @return
     */
    DeliveryChartListResult driverDeliveryChartList(DeliveryChartEnter enter);
}
