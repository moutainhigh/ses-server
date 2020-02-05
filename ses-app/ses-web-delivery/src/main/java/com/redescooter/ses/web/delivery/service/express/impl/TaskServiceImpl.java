package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderEventEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.task.TaskStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.TenantConfigInfoResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.TaskServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;
import com.redescooter.ses.web.delivery.dm.CorExpressDeliveryDetail;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.base.CorDriverService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryDetailService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryService;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderService;
import com.redescooter.ses.web.delivery.service.express.EdOrderTraceService;
import com.redescooter.ses.web.delivery.service.express.TaskService;
import com.redescooter.ses.web.delivery.vo.task.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private CorExpressDeliveryDetailService corExpressDeliveryDetailService;

    @Autowired
    private CorDriverService corDriverService;

    @Reference
    private IdAppService idAppService;

    @Reference
    private ScooterService scooterService;

    @Reference
    private TenantBaseService tenantBaseService;

    @Autowired
    private EdOrderTraceService edOrderTraceService;


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
        if (CollectionUtils.isEmpty(corExpressOrderList) && corExpressOrderList.size()!=enter.getIds().size()) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }

        // 获取店铺配置
        TenantConfigInfoResult tenantConfigInfoResult = tenantBaseService.tenantConfigInfo(enter);

        Long taskId = idAppService.getId(SequenceName.COR_EXPRESS_DELIVERY);

        List<CorExpressDeliveryDetail> corExpressDeliveryDetailList = new ArrayList<>();

        List<BaseExpressOrderTraceEnter> baseExpressOrderTraceEnterList = new ArrayList<>();
        corExpressOrderList.forEach(item -> {
            if (!enter.getIds().contains(item.getId())) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
            }
            // 保存 expressDeliveryDetail
            corExpressDeliveryDetailList.add(buildCorExpressDeliveryDetailSingle(enter, tenantConfigInfoResult, taskId, item));

            //生成 order 记录
            BaseExpressOrderTraceEnter baseExpressOrderTraceEnter = buildBaseExpressOrderTraceEnter(enter,
                    tenantConfigInfoResult, taskId, item);
            baseExpressOrderTraceEnterList.add(baseExpressOrderTraceEnter);
            //修改expressOrder 状态
            item.setStatus(ExpressOrderStatusEnums.ASGN.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());

        });
        //        保存task
        buildtask(enter, tenantConfigInfoResult,taskId);
        // 保存 TaskDetail
        if (CollectionUtils.isNotEmpty(corExpressDeliveryDetailList)) {
            corExpressDeliveryDetailService.batchInsert(corExpressDeliveryDetailList);
        }

        // 修改express Order 状态
        corExpressOrderService.updateBatch(corExpressOrderList);
        // 保存taskDetailTrace
        edOrderTraceService.batchSaveExpressOrderTrace(baseExpressOrderTraceEnterList);

        return new GeneralResult(enter.getRequestId());
    }

    private void buildtask(SaveTaskEnter enter, TenantConfigInfoResult tenantConfigInfoResult,Long taskId) {
        CorExpressDelivery corExpressDelivery=new CorExpressDelivery();
        corExpressDelivery.setId(taskId);
        corExpressDelivery.setDr(0);
        corExpressDelivery.setTenantId(tenantConfigInfoResult.getTenantId());
        corExpressDelivery.setStatus(TaskStatusEnums.PENDING.getValue());
        corExpressDelivery.setDriverId(enter.getDiverId());
        corExpressDelivery.setOrderSum(enter.getIds().size());
        corExpressDelivery.setOrderCompleteNum(0);
        corExpressDelivery.setDeliveryDate(DateUtil.stringToDate(enter.getTaskTime()));
        corExpressDelivery.setCreateBy(enter.getUserId());
        corExpressDelivery.setCreateTime(new Date());
        corExpressDelivery.setUpdatedBy(enter.getUserId());
        corExpressDelivery.setUpdatedTime(new Date());
        corExpressDeliveryService.insertOrUpdateSelective(corExpressDelivery);
    }

    private BaseExpressOrderTraceEnter buildBaseExpressOrderTraceEnter(SaveTaskEnter enter,
                                                                       TenantConfigInfoResult tenantConfigInfoResult, Long taskId, CorExpressOrder item) {
        return BaseExpressOrderTraceEnter.builder()
                .id(idAppService.getId(SequenceName.COR_EXPRESS_ORDER_TRACE))
                .dr(0)
                .expressDeliveryId(taskId)
                .expressOrderId(item.getId())
                .tenantId(enter.getTenantId())
                .driverId(enter.getDiverId())
                .status(ExpressOrderStatusEnums.ASGN.getValue())
                .event(ExpressOrderEventEnums.ASGN.getValue())
                .reason(null)
                .eventTime(new Date())
                .longitude(tenantConfigInfoResult.getLongitude())
                .latitude(tenantConfigInfoResult.getLatitude())
                .geohash(MapUtil.geoHash(tenantConfigInfoResult.getLongitude().toString(),tenantConfigInfoResult.getLatitude().toString()))
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * buildCorExpressDelivery
     *
     * @param enter
     * @param taskId
     * @return
     */
    private CorExpressDelivery buildCorExpressDelivery(SaveTaskEnter enter, Long taskId) {
        CorExpressDelivery corExpressDelivery = new CorExpressDelivery();
        corExpressDelivery.setId(taskId);
        corExpressDelivery.setDr(0);
        corExpressDelivery.setTenantId(enter.getTenantId());
        corExpressDelivery.setStatus(TaskStatusEnums.PENDING.getValue());
        corExpressDelivery.setDriverId(enter.getDiverId());
        corExpressDelivery.setOrderSum(enter.getIds().size());
        corExpressDelivery.setOrderCompleteNum(0);
        corExpressDelivery.setDeliveryDate(DateUtil.parse(enter.getTaskTime(), DateUtil.DEFAULT_DATETIME_FORMAT));
        corExpressDelivery.setCreateBy(enter.getUserId());
        corExpressDelivery.setCreateTime(new Date());
        corExpressDelivery.setUpdatedBy(enter.getUserId());
        corExpressDelivery.setUpdatedTime(new Date());
        return corExpressDelivery;
    }

    /**
     * buildCorExpressDeliveryDetailSingle
     *
     * @param enter
     * @param tenantConfigInfoResult
     * @param taskId
     * @param item
     * @return
     */
    private CorExpressDeliveryDetail buildCorExpressDeliveryDetailSingle(SaveTaskEnter enter, TenantConfigInfoResult tenantConfigInfoResult, Long taskId, CorExpressOrder item) {
        CorExpressDeliveryDetail corExpressDeliveryDetail = new CorExpressDeliveryDetail();
        corExpressDeliveryDetail.setId(idAppService.getId(SequenceName.COR_EXPRESS_DELIVERY_DETAIL));
        corExpressDeliveryDetail.setDr(0);
        corExpressDeliveryDetail.setTenantId(enter.getTenantId());
        corExpressDeliveryDetail.setExpressDeliveryId(taskId);
        corExpressDeliveryDetail.setExpressOrderId(item.getId());
        corExpressDeliveryDetail.setStatus(ExpressOrderStatusEnums.ASGN.getValue());
        corExpressDeliveryDetail.setParcelQuantity(1);
        // todo 暂时以当前时间 加店铺超时时间
        corExpressDeliveryDetail.setAta(DateUtils.addMinutes(new Date(), tenantConfigInfoResult.getEstimatedDuration().intValue()));
        corExpressDeliveryDetail.setPrioritySort(0);
        corExpressDeliveryDetail.setCreatedBy(enter.getUserId());
        corExpressDeliveryDetail.setCreatedTime(new Date());
        corExpressDeliveryDetail.setUpdatedBy(enter.getUserId());
        corExpressDeliveryDetail.setUpdatedTime(new Date());
        return corExpressDeliveryDetail;
    }
}
