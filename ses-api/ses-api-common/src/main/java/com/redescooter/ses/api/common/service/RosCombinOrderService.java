package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * ROS项目组装单业务接口
 * @author assert
 * @date 2021/1/26 14:52
 */
public interface RosCombinOrderService {

    /**
     * 生成组装质检单
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/26
    */
    GeneralResult generatorQcOrderByCombin(IdEnter enter);

}
