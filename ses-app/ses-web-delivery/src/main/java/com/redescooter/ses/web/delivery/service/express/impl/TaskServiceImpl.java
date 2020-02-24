package com.redescooter.ses.web.delivery.service.express.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.expressDelivery.ExpressDeliveryDetailStatusEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderEventEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.jiguang.PlatformTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MesageBizTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MesageTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MessagePriorityEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.task.TaskStatusEnums;
import com.redescooter.ses.api.common.enums.task.TaskTimeCountEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.api.common.vo.message.PushMsgBo;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.PushService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.api.foundation.vo.tenant.TenantConfigInfoResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.TaskServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;
import com.redescooter.ses.web.delivery.dm.CorExpressDeliveryDetail;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.base.CorDriverScooterService;
import com.redescooter.ses.web.delivery.service.base.CorDriverService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryDetailService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryService;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderService;
import com.redescooter.ses.web.delivery.service.express.EdOrderTraceService;
import com.redescooter.ses.web.delivery.service.express.TaskService;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import com.redescooter.ses.web.delivery.vo.task.DriverTaskEnter;
import com.redescooter.ses.web.delivery.vo.task.OrderListEnter;
import com.redescooter.ses.web.delivery.vo.task.OrderResult;
import com.redescooter.ses.web.delivery.vo.task.SaveTaskEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskListEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskResult;
import com.redescooter.ses.web.delivery.vo.task.TaskTimeCountDto;

import lombok.extern.slf4j.Slf4j;

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

    @Autowired
    private EdOrderTraceService edOrderTraceService;

    @Autowired
    private CorDriverScooterService corDriverScooterService;

    @Reference
    private IdAppService idAppService;

    @Reference
    private ScooterService scooterService;

    @Reference
    private TenantBaseService tenantBaseService;

    @Reference
    private PushService pushService;

    @Reference
    private CityBaseService cityBaseService;

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
     * 任务时间统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> taskTimeCount(GeneralEnter enter) {

        TaskTimeCountDto taskTimeCountDto = taskServiceMapper.taskTimeCount(enter);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put(TaskTimeCountEnums.TODAY_NUM.getValue(), taskTimeCountDto.getTnum());
        resultMap.put(TaskTimeCountEnums.HISTORY_NUM.getValue(), taskTimeCountDto.getHnum());

        return resultMap;
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
        TaskResult result = taskServiceMapper.detail(enter);;
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(result.getScooterId());
        List<BaseScooterResult> baseScooterResults = scooterService.scooterInfor(scooterIdList);
        if (CollectionUtils.isNotEmpty(baseScooterResults)){
            TaskResult finalResult = result;
            baseScooterResults.forEach(itme -> {
                Optional.ofNullable(itme).ifPresent(it -> {
                    finalResult.setBattery(baseScooterResults.get(0).getBattery());
                    finalResult.setMileage(baseScooterResults.get(0).getTotalmileage().toString());
                });
            });
        }

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
    public List<DriverListResult> driverList(GeneralEnter enter) {
        return taskServiceMapper.driverList(enter);
    }

    /**
     * 未分配小定单列表
     *
     * @param enter
     */
    @Override
    public PageResult<OrderResult> orderList(OrderListEnter enter) {

        if(StringUtils.isNotBlank(enter.getRecipientCity())){
            CityResult recipientCity = cityBaseService.queryCityDeatliById(new IdEnter(Long.parseLong(enter.getRecipientCity())));
            enter.setRecipientCity(recipientCity != null ? recipientCity.getName() : null);

        }
        if(StringUtils.isNotBlank(enter.getRecipientPostcode())){
            CityResult recipientPostcode = cityBaseService.queryCityDeatliById(new IdEnter(Long.parseLong(enter.getRecipientPostcode())));
            enter.setRecipientPostcode(recipientPostcode != null ? recipientPostcode.getName() : null);
        }

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

        List<DriverTaskEnter> driverTaskEnters = null;
        try {
            driverTaskEnters = JSONArray.parseArray(enter.getDriverTaskListJson(), DriverTaskEnter.class);
        } catch (Exception e) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //验证订单是否分配完成
        OrderListEnter conutOrders = new OrderListEnter();
        BeanUtils.copyProperties(enter, conutOrders);
        int count = taskServiceMapper.orderListCount(conutOrders);
        List<Long> longs = new ArrayList<>();
        driverTaskEnters.forEach(ds -> {
            Optional.ofNullable(ds).ifPresent(d -> {
                longs.addAll(d.getIds());
            });
        });

        Boolean saveBoolean = count == longs.size() ? Boolean.TRUE : Boolean.FALSE;

        if (saveBoolean) {
            // 获取店铺配置
            TenantConfigInfoResult tenantConfigInfoResult = tenantBaseService.tenantConfigInfo(enter);
            // 大订单详情列表
            List<CorExpressDeliveryDetail> corExpressDeliveryDetailList = new ArrayList<>();
            // 大订单节点
            List<BaseExpressOrderTraceEnter> baseExpressOrderTraceEnterList = new ArrayList<>();
            // 小订单更新 集合
            List<CorExpressOrder> updateCorExpressOrderList = new ArrayList<>();
            // 大订单集合
            List<CorExpressDelivery> saveCorExpressDeliveryList = new ArrayList<>();

            driverTaskEnters.forEach(driverTaskEnter -> {
                CorDriver corDriver = corDriverService.getById(driverTaskEnter.getDiverId());
                if (corDriver == null) {
                    throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
                }
                // 查询司机当前正在使用的车辆
                QueryWrapper<CorDriverScooter> corDriverScooterQueryWrapper = new QueryWrapper<>();
                corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, corDriver.getId());
                corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
                corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
                corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
                CorDriverScooter corDriverScooter = corDriverScooterService.getOne(corDriverScooterQueryWrapper);

                List<CorExpressOrder> corExpressOrderList = taskServiceMapper.queryExpressOrderByIds(driverTaskEnter.getIds());
                if (CollectionUtils.isEmpty(corExpressOrderList) && corExpressOrderList.size() != driverTaskEnter.getIds().size()) {
                    throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
                }

                Long taskId = idAppService.getId(SequenceName.COR_EXPRESS_DELIVERY);
                for (CorExpressOrder item : corExpressOrderList) {
                    if (!driverTaskEnter.getIds().contains(item.getId())) {
                        throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(),
                                ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
                    }
                    if (!StringUtils.equals(item.getStatus(), ExpressOrderStatusEnums.UNASGN.getValue())) {
                        throw new SesWebDeliveryException(ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getCode(), ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getMessage());
                    }

                    // 保存 expressDeliveryDetail
                    corExpressDeliveryDetailList.add(buildCorExpressDeliveryDetailSingle(enter, tenantConfigInfoResult,
                            taskId, item));
                    //生成 order 记录
                    BaseExpressOrderTraceEnter baseExpressOrderTraceEnter = buildBaseExpressOrderTraceEnter(enter,
                            tenantConfigInfoResult, driverTaskEnter.getDiverId(), taskId, item);
                    baseExpressOrderTraceEnterList.add(baseExpressOrderTraceEnter);
                    //修改expressOrder 状态
                    item.setAssignFlag(Boolean.TRUE);
                    item.setAssignTime(new Date());
                    item.setStatus(ExpressOrderStatusEnums.ASGN.getValue());
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                    updateCorExpressOrderList.add(item);
                }
                //保存task
                buildtask(enter, driverTaskEnter, tenantConfigInfoResult, taskId, saveCorExpressDeliveryList, corDriverScooter.getScooterId());
            });

            // 保存 TaskDetail
            if (CollectionUtils.isNotEmpty(corExpressDeliveryDetailList)) {
                corExpressDeliveryDetailService.batchInsert(corExpressDeliveryDetailList);
            }
            // 保存大订单
            if (CollectionUtils.isNotEmpty(saveCorExpressDeliveryList)) {
                corExpressDeliveryService.batchInsert(saveCorExpressDeliveryList);
            }
            // 修改express Order 状态
            if (CollectionUtils.isNotEmpty(updateCorExpressOrderList)) {
                corExpressOrderService.updateBatch(updateCorExpressOrderList);
            }
            // 保存taskDetailTrace
            if (CollectionUtils.isNotEmpty(updateCorExpressOrderList)) {
                edOrderTraceService.batchSaveExpressOrderTrace(baseExpressOrderTraceEnterList);
            }

            // web ---》 app 消息推送
            saveCorExpressDeliveryList.forEach(item -> {
                //todo args 规则 参数1 小定单订单号 参数2 大订单id 后面参数不做限定
                Object[] args = new Object[]{"0", item.getId()};

                CorDriver corDriver = corDriverService.getById(item.getDriverId());
                PushMsgBo pushApp = PushMsgBo.builder()
                        .enter(enter)
                        .status(TaskStatusEnums.PENDING.getValue())
                        .args(args)
                        .bizType(MesageBizTypeEnum.EXPRESS_DELIVERY.getValue())
                        .bizId(item.getId())
                        .belongId(corDriver.getUserId())
                        .appId(AppIDEnums.SAAS_APP.getAppId())
                        .systemId(AppIDEnums.SAAS_APP.getSystemId())
                        .pushType(PlatformTypeEnum.ANDROID.getValue())
                        .messagePriority(MessagePriorityEnums.NONE_REMIND.getValue())
                        .mesageType(MesageTypeEnum.PUSH.getValue())
                        .build();
                pushMsg(pushApp);
            });

        } else {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ORDER_IS_NOT_ALLOCATED.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_ALLOCATED.getMessage());
        }
        return new GeneralResult(enter.getRequestId());
    }

    private void buildtask(SaveTaskEnter enter, DriverTaskEnter driverTaskEnter, TenantConfigInfoResult tenantConfigInfoResult, Long taskId, List<CorExpressDelivery> saveCorExpressDeliveryList,
                           Long scooterId) {
        CorExpressDelivery corExpressDelivery = new CorExpressDelivery();
        corExpressDelivery.setId(taskId);
        corExpressDelivery.setDr(0);
        corExpressDelivery.setScooterId(scooterId);
        corExpressDelivery.setTenantId(tenantConfigInfoResult.getTenantId());
        corExpressDelivery.setStatus(TaskStatusEnums.PENDING.getValue());
        corExpressDelivery.setDriverId(driverTaskEnter.getDiverId());
        corExpressDelivery.setOrderSum(driverTaskEnter.getIds().size());
        corExpressDelivery.setOrderCompleteNum(0);
        corExpressDelivery.setDeliveryDate(enter.getTaskTime());
        corExpressDelivery.setDrivenMileage(BigDecimal.ZERO);
        corExpressDelivery.setResult(null);
        corExpressDelivery.setDrivenDuration(0);
        corExpressDelivery.setCo2(BigDecimal.ZERO);
        corExpressDelivery.setSavings(BigDecimal.ZERO);
        corExpressDelivery.setCreateBy(enter.getUserId());
        corExpressDelivery.setCreateTime(new Date());
        corExpressDelivery.setUpdatedBy(enter.getUserId());
        corExpressDelivery.setUpdatedTime(new Date());
        saveCorExpressDeliveryList.add(corExpressDelivery);
    }

    private BaseExpressOrderTraceEnter buildBaseExpressOrderTraceEnter(SaveTaskEnter enter,
                                                                       TenantConfigInfoResult tenantConfigInfoResult, Long driverId, Long taskId, CorExpressOrder item) {
        return BaseExpressOrderTraceEnter.builder()
                .id(idAppService.getId(SequenceName.COR_EXPRESS_ORDER_TRACE))
                .dr(0)
                .expressDeliveryId(taskId)
                .expressOrderId(item.getId())
                .tenantId(enter.getTenantId())
                .driverId(driverId)
                .status(ExpressOrderStatusEnums.ASGN.getValue())
                .event(ExpressOrderEventEnums.ASGN.getValue())
                .reason(null)
                .eventTime(new Date())
                .longitude(tenantConfigInfoResult.getLongitude())
                .latitude(tenantConfigInfoResult.getLatitude())
                .geohash(MapUtil.geoHash(tenantConfigInfoResult.getLongitude().toString(), tenantConfigInfoResult.getLatitude().toString()))
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
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
        corExpressDeliveryDetail.setStatus(ExpressDeliveryDetailStatusEnums.ASGN.getValue());
        corExpressDeliveryDetail.setParcelQuantity(1);
        // todo 暂时以当前时间 加店铺超时时间
        corExpressDeliveryDetail.setAta(DateUtils.addMinutes(new Date(), tenantConfigInfoResult.getEstimatedDuration().intValue()));
        //enter.getIds().indexOf(item.getId())+1
        corExpressDeliveryDetail.setPrioritySort(0);
        corExpressDeliveryDetail.setCreatedBy(enter.getUserId());
        corExpressDeliveryDetail.setCreatedTime(new Date());
        corExpressDeliveryDetail.setUpdatedBy(enter.getUserId());
        corExpressDeliveryDetail.setUpdatedTime(new Date());
        return corExpressDeliveryDetail;
    }


    private void pushMsg(PushMsgBo pushMsg) {

        String generalEnter = JSON.toJSONString(pushMsg.getEnter());
        Map<String, String> pushParameter = new HashMap<>();


        StringBuffer argsString = new StringBuffer();
        if (pushMsg.getArgs() != null) {
            for (int i = 0; i < pushMsg.getArgs().length; i++) {
                argsString.append(pushMsg.getArgs()[i]);
                if (i < pushMsg.getArgs().length - 1) {
                    argsString.append(",");
                }
            }
        } else {
            argsString.append("0");
        }

        String title = pushMsg.getBizType() + "_" + pushMsg.getSystemId() + "_" + pushMsg.getAppId() + "_" + pushMsg.getStatus() + "_TITLE";
        String content = pushMsg.getBizType() + "_" + pushMsg.getSystemId() + "_" + pushMsg.getAppId() + "_" + pushMsg.getStatus() + "_CONTENT";


        pushParameter.put("BizType", pushMsg.getBizType());
        pushParameter.put("Id", String.valueOf(pushMsg.getBizId()));
        pushParameter.put("Type", pushMsg.getStatus());
        pushParameter.put("args", argsString.toString());
        pushParameter.put("title", title);
        pushParameter.put("content", content);
        pushParameter.put("bussinessStatus", pushMsg.getStatus());
        pushParameter.put("messagePriority", StringUtils.isEmpty(pushMsg.getMessagePriority()) == true ? MessagePriorityEnums.NONE_REMIND.getValue() : pushMsg.getMessagePriority());
        pushParameter.put("mesageType", StringUtils.isEmpty(pushMsg.getMesageType()) == true ? MesageTypeEnum.NONE.getValue() : pushMsg.getMesageType());

        pushParameter.put("generalEnter", generalEnter);

        // 消息所推对象参数
        pushParameter.put("userIds", String.valueOf(pushMsg.getBelongId()));
        pushParameter.put("createUser", pushMsg.getEnter().getUserId().toString());
        pushParameter.put("appId", pushMsg.getAppId());
        pushParameter.put("systemId", pushMsg.getSystemId());
        pushParameter.put("pushType", pushMsg.getPushType());
        pushService.pushMessage(JSON.toJSONString(pushParameter));
    }
}
