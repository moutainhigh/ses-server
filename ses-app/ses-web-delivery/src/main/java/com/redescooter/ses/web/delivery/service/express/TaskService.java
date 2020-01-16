package com.redescooter.ses.web.delivery.service.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import com.redescooter.ses.web.delivery.vo.task.OrderListEnter;
import com.redescooter.ses.web.delivery.vo.task.OrderResult;
import com.redescooter.ses.web.delivery.vo.task.SaveTaskEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskListEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:DeliveryService
 * @description: DeliveryService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 14:53
 */
public interface TaskService {

    /**
     * 状态
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countByStatus(GeneralEnter enter);

    /**
     * 车辆列表
     *
     * @param enter
     * @return
     */
    PageResult<TaskResult> list(TaskListEnter enter);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    TaskResult detail(IdEnter enter);

    /**
     * 详情小定单列表
     *
     * @param enter
     * @return
     */
    List<OrderResult> detailOrderList(IdEnter enter);

    /**
     * 司机列表
     *
     * @param enter
     * @return
     */
    DriverListResult driverList(GeneralEnter enter);

    /**
     * 未分配小定单列表
     *
     * @param enter
     */
    PageResult<OrderResult> orderList(OrderListEnter enter);

    /**
     * 大订单 保存
     *
     * @return
     */
    GeneralResult save(SaveTaskEnter enter);
}
