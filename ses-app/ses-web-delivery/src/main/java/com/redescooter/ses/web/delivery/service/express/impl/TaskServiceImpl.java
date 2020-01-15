package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.service.express.TaskService;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import com.redescooter.ses.web.delivery.vo.task.OrderResult;
import com.redescooter.ses.web.delivery.vo.task.SaveTaskEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskListEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:TaskServiceImpl
 * @description: TaskServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 17:29
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    /**
     * 状态
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByStatus(GeneralEnter enter) {

        return null;
    }

    /**
     * 车辆列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<TaskResult> list(TaskListEnter enter) {

        return null;
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public TaskResult detail(IdEnter enter) {
        return null;
    }

    /**
     * 详情小定单列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<OrderResult> detailOrderList(IdEnter enter) {
        return null;
    }

    /**
     * 司机列表
     *
     * @param enter
     * @return
     */
    @Override
    public DriverListResult driverList(GeneralEnter enter) {
        return null;
    }

    /**
     * 小定单列表
     *
     * @param enter
     */
    @Override
    public PageResult<OrderResult> orderList(GeneralEnter enter) {
        return null;
    }

    /**
     * 大订单 保存
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SaveTaskEnter enter) {
        return null;
    }
}
