package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.vo.task.*;

import java.util.List;

/**
 * @ClassName:TaskService
 * @description: TaskService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/16 09:52
 */
public interface TaskServiceMapper {

    /**
     * task 状态 统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByStatus(GeneralEnter enter);

    /**
     * task 统计
     *
     * @param enter
     * @return
     */
    int taskCount(TaskListEnter enter);

    /**
     * 任务条目
     *
     * @param enter
     * @return
     */
    List<TaskResult> taskList(TaskListEnter enter);

    /**
     * 任务详情
     *
     * @param enter
     * @return
     */
    TaskResult detail(IdEnter enter);

    /**
     * task 详情小定单列表
     *
     * @param enter
     * @return
     */
    List<OrderResult> detailOrderList(IdEnter enter);

    /**
     * 快递司机列表
     *
     * @param enter
     * @return
     */
    List<DriverListResult> driverList(GeneralEnter enter);

    /**
     * 添加任务小定单列表
     *
     * @param enter
     * @return
     */
    int orderListCount(OrderListEnter enter);

    /**
     * 添加任务小定单列表
     *
     * @param enter
     * @return
     */
    List<OrderResult> orderList(OrderListEnter enter);

    /**
     * 查询快递订单
     *
     * @param ids
     * @return
     */
    List<CorExpressOrder> queryExpressOrderByIds(List<Long> ids);
}
