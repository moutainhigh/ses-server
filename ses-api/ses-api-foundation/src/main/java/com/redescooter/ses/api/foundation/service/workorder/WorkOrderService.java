package com.redescooter.ses.api.foundation.service.workorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.workorder.*;

/**
 * @ClassNameWorkOrderService
 * @Description
 * @Author Aleks
 * @Date2020/12/4 15:32
 * @Version V1.0
 **/
public interface WorkOrderService {

    /**
     * @Author Aleks
     * @Description  工单列表
     * @Date  2020/12/4 14:00
     * @Param []
     * @return
     **/
    PageResult<WorkOrderListResult> workOrderList (WorkOrderListEnter enter);


    /**
     * @Author Aleks
     * @Description  工单新增
     * @Date  2020/12/4 14:23
     * @Param
     * @return
     **/
    GeneralResult workOrderSave(WorkOrderSaveOrUpdateEnter dto);


    /**
     * @Author Aleks
     * @Description  工单编辑
     * @Date  2020/12/4 15:00
     * @Param [dto]
     * @return
     **/
    GeneralResult workOrderUpdate(WorkOrderSaveOrUpdateEnter dto);


    /**
     * @Author Aleks
     * @Description  工单删除
     * @Date  2020/12/4 15:00
     * @Param [dto]
     * @return
     **/
    GeneralResult workOrderDelete(IdEnter dto);


    /**
     * @Author Aleks
     * @Description  工单详情
     * @Date  2020/12/4 16:27
     * @Param
     * @return
     **/
    WorkOrderDetailResult workOrderDetail(IdEnter dto);


    /**
     * @Author Aleks
     * @Description  工单状态流转(只能往当前状态的后面的状态跳  随便跳)
     * @Date  2020/12/4 18:06
     * @Param
     * @return
     **/
    GeneralResult workOrderStatusFlow(StatusFlowEnter enter);


    /**
     * @Author Aleks
     * @Description  工单回复
     * @Date  2020/12/4 17:27
     * @Param [enter]
     * @return
     **/
    GeneralResult workOrderReply(workOrderReplyEnter enter);
}
