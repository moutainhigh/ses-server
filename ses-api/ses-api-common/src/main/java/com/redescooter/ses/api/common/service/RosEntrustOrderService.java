package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * ROS项目委托单业务接口
 * @author assert
 * @date 2021/1/7 17:04
 */
public interface RosEntrustOrderService {

    /**
     * 委托单发货
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/7
    */
    GeneralResult entrustOrderDeliver(IdEnter enter);

}
