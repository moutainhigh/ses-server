package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 6:54 上午
 * @ClassName: DriverService
 * @Function: TODO
 */
public interface DriverService {

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

    Map<String,Integer> driverDeliveryCountByStatus();
}
