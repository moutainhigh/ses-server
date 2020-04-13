package com.redescooter.ses.web.delivery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import com.redescooter.ses.web.delivery.vo.task.OrderListEnter;
import com.redescooter.ses.web.delivery.vo.task.OrderResult;
import com.redescooter.ses.web.delivery.vo.task.TaskListEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskResult;
import com.redescooter.ses.web.delivery.vo.task.TaskTimeCountDto;

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
     * 任务时间统计
     *
     * @param enter
     * @return
     */
    TaskTimeCountDto taskTimeCount(GeneralEnter enter);

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

    /**
     * 获取司机信息 通过taskId
     * @param enter
     * @return
     */
    CorDriver queryDriverByTaskId(IdEnter enter);

    /**
     * 获取个人信息 通过taskId
     * @param driverId
     * @param tenantId
     * @return
     */
    TaskResult driverOffUserProfileByDriverId(@Param("driverId") Long driverId, @Param("tenantId") Long tenantId);
}
