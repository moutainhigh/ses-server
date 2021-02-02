package com.redescooter.ses.mobile.rps.dao.order;

import com.redescooter.ses.mobile.rps.dm.OpeOrderStatusFlow;

/**
 * 单据状态流转相关 Mapper接口
 * @author assert
 * @date 2021/1/4 19:01
 */
public interface OrderStatusFlowMapper {

    /**
     * 新增单据状态流转信息
     * @param orderStatusFlow
     * @return int
     * @author assert
     * @date 2021/1/5
    */
    int insertOrderStatusFlow(OpeOrderStatusFlow orderStatusFlow);

}
