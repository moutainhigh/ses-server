package com.redescooter.ses.mobile.rps.service.order;

/**
 * 订单操作记录业务接口
 * @author assert
 * @date 2021/1/18 11:19
 */
public interface OpTraceService {

    /**
     * 新增订单操作记录
     * @param orderId, orderType, opType, remark, userId
     * @return int
     * @author assert
     * @date 2021/1/18
    */
    int insertOpTrace(Long orderId, Integer orderType, Integer opType, String remark, Long userId);

}
