package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;

import java.util.List;

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
     * @Description  调拨单列表
     * @Date  2020/10/23 12:05
     * @Param [enter]
     * @return
     **/
    PageResult<AllocateOrderListResult> allocateList(AllocateOrderListEnter enter);


    /**
     * @Author Aleks
     * @Description 调拨单新增
     * @Date  2020/10/23 14:38
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateSave(AllocateOrderOrUpdateSaveEnter enter);


    /**
     * @Author Aleks
     * @Description
     * @Date  2020/10/23 16:01
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateEdit(AllocateOrderOrUpdateSaveEnter enter);


    /**
     * @Author Aleks
     * @Description  调拨单详情
     * @Date  2020/10/23 16:31
     * @Param [enter]
     * @return
     **/
    AllocateOrderDetailResult allocateDetail(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  调拨单立即下单
     * @Date  2020/10/23 17:48
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateConfirmOrder(IdEnter enter);


    /**
     * @Author Aleks
     * @Description 取消订单
     * @Date  2020/10/23 17:50
     * @Param [enter]
     * @return
     **/
    GeneralResult allocateCancelOrder(IdEnter enter);


    /**
     * @Author Aleks
     * @Description 人员下拉数据
     * @Date  2020/10/23 17:50
     * @Param [enter]
     * @return
     **/
    List<UserDataResult> userData(UserDataEnter enter);





}
