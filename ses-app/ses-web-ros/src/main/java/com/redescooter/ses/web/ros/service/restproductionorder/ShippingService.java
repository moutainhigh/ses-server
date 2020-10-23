package com.redescooter.ses.web.ros.service.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;

import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/23 12:12
 *  @version：V ROS 1.8.3
 *  @Description:
 */
public interface ShippingService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 12:13
     * @Param: enter
     * @Return: Map
     * @desc: 状态分组
     */
    Map<String, Integer> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 12:16
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     */
    Map<String, Integer> statusList(GeneralEnter enter);

    GeneralResult list();
}
