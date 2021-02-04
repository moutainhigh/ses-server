package com.redescooter.ses.mobile.rps.service.restproductionorder.allocateorder;

/**
 * @ClassNameAllocateOrderService
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:33
 * @Version V1.0
 **/
public interface AllocateOrderService {

    /**
     * @Author Aleks
     * @Description  调拨单的状态变为待签收
     * @Date  2020/10/30 19:26
     * @Param [allocateId, userId]
     * @return
     **/
    void allocateWaitSign(Long allocateId,Long userId);


}
