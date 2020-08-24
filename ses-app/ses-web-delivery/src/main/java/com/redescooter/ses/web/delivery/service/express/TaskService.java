package com.redescooter.ses.web.delivery.service.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.task.*;

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
     * 状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countByStatus(GeneralEnter enter);

    /**
     * 任务时间统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> taskTimeCount(GeneralEnter enter);

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
    List<DriverListResult> driverList(TaskDriverLsitEnter enter);

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
