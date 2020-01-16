package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.enums.task.TaskStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.web.delivery.dao.TaskServiceMapper;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.express.TaskService;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import com.redescooter.ses.web.delivery.vo.task.OrderResult;
import com.redescooter.ses.web.delivery.vo.task.SaveTaskEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskListEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private TaskServiceMapper taskServiceMapper;

    @Reference
    private ScooterService scooterService;

    /**
     * 状态
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = taskServiceMapper.countByStatus(enter);
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResultList) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (TaskStatusEnums status : TaskStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 车辆列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<TaskResult> list(TaskListEnter enter) {
        int count = taskServiceMapper.taskCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, taskServiceMapper.taskList(enter));
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public TaskResult detail(IdEnter enter) {
        TaskResult result = taskServiceMapper.detail(enter);
        if (result == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.TASK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TASK_IS_NOT_EXIST.getMessage());
        }
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(result.getScooterId());
        List<BaseScooterResult> baseScooterResults = scooterService.scooterInfor(scooterIdList);
        result.setBattery(baseScooterResults.get(0).getBattery());
        result.setMileage(baseScooterResults.get(0).getTotalmileage().toString());
        return result;
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
