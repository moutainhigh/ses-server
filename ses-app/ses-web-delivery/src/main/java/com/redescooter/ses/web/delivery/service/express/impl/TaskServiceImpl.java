package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.enums.task.TaskStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.delivery.dao.TaskServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorExpressDeliveryDetail;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.base.CorDriverService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryService;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderService;
import com.redescooter.ses.web.delivery.service.express.TaskService;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import com.redescooter.ses.web.delivery.vo.task.OrderListEnter;
import com.redescooter.ses.web.delivery.vo.task.OrderResult;
import com.redescooter.ses.web.delivery.vo.task.SaveTaskEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskListEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CorExpressDeliveryService corExpressDeliveryService;

    @Autowired
    private CorExpressOrderService corExpressOrderService;

    @Autowired
    private CorDriverService corDriverService;

    @Reference
    private IdAppService idAppService;

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
        Map<String, Integer> map = new HashMap<>(10);
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
        List<OrderResult> orderResultList = taskServiceMapper.detailOrderList(enter);
        if (orderResultList == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.TASK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TASK_IS_NOT_EXIST.getMessage());
        }
        return orderResultList;
    }

    /**
     * 司机列表
     *
     * @param enter
     * @return
     */
    @Override
    public DriverListResult driverList(GeneralEnter enter) {
        return taskServiceMapper.driverList(enter);
    }

    /**
     * 未分配小定单列表
     *
     * @param enter
     */
    @Override
    public PageResult<OrderResult> orderList(OrderListEnter enter) {
        int count = taskServiceMapper.orderListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OrderResult> orderResultList = taskServiceMapper.orderList(enter);
        return PageResult.create(enter, count, orderResultList);
    }

    /**
     * 大订单 保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SaveTaskEnter enter) {
        CorDriver corDriver = corDriverService.getById(enter.getDiverId());
        if (corDriver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        List<CorExpressOrder> corExpressOrderList = taskServiceMapper.queryExpressOrderByIds(enter.getIds());
        if (CollectionUtils.isEmpty(corExpressOrderList)) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }

        List<CorExpressDeliveryDetail> corExpressDeliveryDetailList = new ArrayList<>();

        corExpressOrderList.forEach(item -> {
            if (!enter.getIds().contains(item.getId())) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
            }
// todo 未完成
            //            CorExpressDeliveryDetail corExpressDeliveryDetail=new CorExpressDeliveryDetail();
//            corExpressDeliveryDetail.setId(idAppService.getId(SequenceName.COR_EXPRESS_DELIVERY_DETAIL));
//            corExpressDeliveryDetail.set
            // 创建 任务详情
        });
        return null;
    }
}
