package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderDetailResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderListEnter;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderListResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderLogResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameWorkOrderMapper
 * @Description
 * @Author Aleks
 * @Date2020/12/4 17:19
 * @Version V1.0
 **/
public interface WorkOrderMapper {

   int workOrderListCount(@Param("enter") WorkOrderListEnter enter);

    List<WorkOrderListResult> workOrderList(@Param("enter") WorkOrderListEnter enter);

    WorkOrderDetailResult workOrderDetail(@Param("enter") IdEnter enter);

    List<WorkOrderLogResult> workOrderLogs(@Param("workOrderId")Long workOrderId);


}
