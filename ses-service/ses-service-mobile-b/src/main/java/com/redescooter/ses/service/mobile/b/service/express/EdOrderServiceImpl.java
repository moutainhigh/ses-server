package com.redescooter.ses.service.mobile.b.service.express;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.expressDelivery.ExpressDeliveryDetailStatusEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderEventEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.mesage.MesageBizTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MesageTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MessagePriorityEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PlatformTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.enums.task.TaskStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.api.common.vo.message.PushMsgBo;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.foundation.service.PushService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.express.EdOrderDeliveryTraceService;
import com.redescooter.ses.api.mobile.b.service.express.EdOrderService;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteResult;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import com.redescooter.ses.api.mobile.b.vo.express.EdRfuseEnter;
import com.redescooter.ses.api.mobile.b.vo.express.OrderResult;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.service.mobile.b.constant.SequenceName;
import com.redescooter.ses.service.mobile.b.dao.EdOrderServiceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetail;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import com.redescooter.ses.service.mobile.b.dm.base.CorTenantScooter;
import com.redescooter.ses.service.mobile.b.dm.base.CorUserProfile;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryDetailService;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryService;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderService;
import com.redescooter.ses.service.mobile.b.service.base.CorTenantScooterService;
import com.redescooter.ses.service.mobile.b.service.base.CorUserProfileService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.co2.CO2MoneyConversionUtil;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.tool.utils.map.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
public class EdOrderServiceImpl implements EdOrderService {

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ScooterIotService scooterIotService;

    @DubboReference
    private PushService pushService;

    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @DubboReference
    private UserBaseService userBaseService;

    @Resource
    private EdOrderServiceMapper edOrderServiceMapper;

    @Resource
    private CorExpressOrderService corExpressOrderService;

    @Resource
    private CorExpressDeliveryDetailService corExpressDeliveryDetailService;

    @Resource
    private CorTenantScooterService corTenantScooterService;

    @Resource
    private EdOrderDeliveryTraceService edOrderDeliveryTraceService;

    @Resource
    private CorExpressDeliveryService corExpressDeliveryService;

    @Resource
    private CorUserProfileService corUserProfileService;

    @Resource
    private JedisService jedisService;

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:18
     * @Param: enter
     * @Return: PageResult<OrderResult>
     * @desc: 订单列表
     */
    @Override
    public List<OrderResult> orderList(GeneralEnter enter) {
        List<OrderResult> orderList = edOrderServiceMapper.orderList(enter);
        if (CollectionUtils.isEmpty(orderList)) {
            return null;
        }
        return orderList;
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:30
     * @Param: enter
     * @Return: OrderResult
     * @desc: 订单详情
     */
    @Override
    public OrderResult orderDetail(IdEnter enter) {
        CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
        OrderResult orderResult = new OrderResult();
        BeanUtils.copyProperties(corExpressOrder, orderResult);
        return orderResult;
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:31
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 开始订单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult start(StartEnter enter) {
        // 是否有正在进行的订单
        int count = edOrderServiceMapper.dirverShippingOrder(enter);
        if (count > 0) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getCode(), ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getMessage());
        }
        //验证订单是否存在
        CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
        if (corExpressOrder == null) {
            throw new MobileBException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        // 状态过滤
        if (!StringUtils.equals(corExpressOrder.getStatus(), ExpressOrderStatusEnums.ASGN.getValue())) {
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 开启订单
        // 查询expressDeliveryDetail订单
        QueryWrapper<CorExpressDeliveryDetail> corExpressDeliveryDetailQueryWrapper = new QueryWrapper<>();
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_DR, 0);
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_TENANT_ID, enter.getTenantId());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_STATUS, ExpressDeliveryDetailStatusEnums.ASGN.getValue());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_EXPRESS_ORDER_ID, enter.getId());
        CorExpressDeliveryDetail deliveryDetail = corExpressDeliveryDetailService.getOne(corExpressDeliveryDetailQueryWrapper);

        deliveryDetail.setStatus(ExpressDeliveryDetailStatusEnums.SHIPPING.getValue());
        deliveryDetail.setAtd(new Date());
        // 暂无预计到达时间 暂定为30分钟
        deliveryDetail.setEta(DateUtils.addMinutes(new Date(), Integer.parseInt("30")));
        deliveryDetail.setUpdatedBy(enter.getUserId());
        deliveryDetail.setUpdatedTime(new Date());
        corExpressDeliveryDetailService.updateById(deliveryDetail);

        // 更新expressOrder 信息
        corExpressOrder.setStatus(ExpressOrderStatusEnums.SHIPPING.getValue());
        corExpressOrder.setUpdatedBy(enter.getUserId());
        corExpressOrder.setUpdatedTime(new Date());
        corExpressOrderService.updateById(corExpressOrder);

        // 骑行数据维护
        CorExpressDelivery corExpressDelivery = corExpressDeliveryService.getById(deliveryDetail.getExpressDeliveryId());
        if (corExpressDelivery.getDrivenMileage().equals(null)) {
            corExpressDelivery.setDrivenMileage(new BigDecimal(enter.getMileage()).add(corExpressDelivery.getDrivenMileage()));
        } else {
            corExpressDelivery.setDrivenMileage(new BigDecimal(enter.getMileage()));
        }
        if (corExpressDelivery.getCo2().equals(null)) {
            corExpressDelivery.setCo2(corExpressDelivery.getCo2().add(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(Long.valueOf(enter.getMileage())))));
        } else {
            corExpressDelivery.setCo2(new BigDecimal(enter.getMileage()));
        }
        if (corExpressDelivery.getSavings().equals(null)) {
            corExpressDelivery.setSavings(corExpressDelivery.getSavings().add(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(Long.valueOf(enter.getMileage())))));
        } else {
            corExpressDelivery.setSavings(new BigDecimal(enter.getMileage()));
        } // 刚开始第一单
        if (corExpressDelivery.getOrderCompleteNum() == 0) {
            corExpressDelivery.setStatus(TaskStatusEnums.INPROGRESS.getValue());
            corExpressDelivery.setDeliveryStartTime(new Date());
        }
        corExpressDeliveryService.updateById(corExpressDelivery);

        // 获取正在骑行的车辆记录
        CorDriverScooter corDriverScooter = edOrderServiceMapper.queryScooterIdByUserId(enter.getUserId(), enter.getTenantId());

        /**
         * 开始订单 -- 开启导航
         */
//        ScooterNavigationDTO scooterNavigation = new ScooterNavigationDTO();
//        scooterNavigation.setEvent(CommonEvent.START.getValue());
//        scooterNavigation.setLat(enter.getLat());
//        scooterNavigation.setLng(enter.getLng());
//
//        scooterEmqXService.scooterNavigation(scooterNavigation, corDriverScooter.getScooterId(), getUserServiceType(enter));

        // 开启导航
        IotScooterEnter iotScooterEnter = new IotScooterEnter();
        BeanUtils.copyProperties(enter, iotScooterEnter);
        iotScooterEnter.setId(corDriverScooter.getScooterId());
        iotScooterEnter.setEvent(CommonEvent.START.getValue());
        iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
        iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
        iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
        scooterIotService.navigation(iotScooterEnter);

        // 查询车辆信息
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, corDriverScooter.getScooterId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        CorTenantScooter corTenantScooter = corTenantScooterService.getOne(corTenantScooterQueryWrapper);
        // 记录日志
        savrOrderTrace(enter, enter.getLng(), enter.getLat(),
                deliveryDetail.getExpressDeliveryId(),
                deliveryDetail.getExpressOrderId(),
                corDriverScooter.getDriverId(),
                corDriverScooter.getScooterId()
                , corTenantScooter == null ? new BigDecimal(enter.getLng()) : corTenantScooter.getLongitule()
                , corTenantScooter == null ? new BigDecimal(enter.getLat()) : corTenantScooter.getLatitude()
                , ExpressOrderStatusEnums.SHIPPING.getValue(),
                ExpressOrderEventEnums.SHIPPING.getValue(), null);

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:32
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 拒绝订单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult refuse(EdRfuseEnter enter) {
        try {
            //redis 加锁
            jedisService.lock(JedisConstant.JEDIS_KEY, JedisConstant.DEFAULT_EXPIRE);

            //验证订单是否存在
            CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
            if (corExpressOrder == null) {
                throw new MobileBException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
            }
            // 状态过滤
            if (!StringUtils.equals(corExpressOrder.getStatus(), ExpressOrderStatusEnums.SHIPPING.getValue())) {
                throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            // 拒绝订单
            // 查询expressDeliveryDetail订单
            QueryWrapper<CorExpressDeliveryDetail> corExpressDeliveryDetailQueryWrapper = new QueryWrapper<>();
            corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_DR, 0);
            corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_TENANT_ID, enter.getTenantId());
            corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_EXPRESS_ORDER_ID, enter.getId());
            corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_STATUS, ExpressDeliveryDetailStatusEnums.SHIPPING.getValue());
            CorExpressDeliveryDetail deliveryDetail = corExpressDeliveryDetailService.getOne(corExpressDeliveryDetailQueryWrapper);

            deliveryDetail.setStatus(ExpressDeliveryDetailStatusEnums.REJECTED.getValue());
            deliveryDetail.setUpdatedBy(enter.getUserId());
            deliveryDetail.setUpdatedTime(new Date());
            corExpressDeliveryDetailService.updateById(deliveryDetail);

            // 更新expressOrder 信息
            corExpressOrder.setStatus(ExpressOrderStatusEnums.REJECTED.getValue());
            corExpressOrder.setDenialReason(enter.getReason());
            corExpressOrder.setUpdatedBy(enter.getUserId());
            corExpressOrder.setUpdatedTime(new Date());
            corExpressOrderService.updateById(corExpressOrder);

            //查询task
            CorExpressDelivery corExpressDelivery = corExpressDeliveryService.getById(deliveryDetail.getExpressDeliveryId());
            Boolean taskPush = Boolean.FALSE;
            corExpressDelivery.setOrderCompleteNum(corExpressDelivery.getOrderCompleteNum() + 1);
            if ((corExpressDelivery.getOrderCompleteNum()).equals(corExpressDelivery.getOrderSum())) {
                corExpressDelivery.setStatus(TaskStatusEnums.DELIVERED.getValue());
                corExpressDelivery.setDeliveryEndTime(new Date());

                // 大订单需要推送
                taskPush = Boolean.TRUE;
            }
            // 更新 task 信息
            corExpressDelivery.setUpdatedBy(enter.getUserId());
            corExpressDelivery.setUpdatedTime(new Date());
            corExpressDeliveryService.updateById(corExpressDelivery);


            // 获取正在骑行的车辆记录
            CorDriverScooter corDriverScooter = edOrderServiceMapper.queryScooterIdByUserId(enter.getUserId(), enter.getTenantId());

            // 查询车辆信息
            QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
            corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
            corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, corDriverScooter.getScooterId());
            corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
            CorTenantScooter corTenantScooter = corTenantScooterService.getOne(corTenantScooterQueryWrapper);

            // 结束导航
            IotScooterEnter iotScooterEnter = new IotScooterEnter();
            BeanUtils.copyProperties(enter, iotScooterEnter);
            iotScooterEnter.setId(corDriverScooter.getScooterId());
            iotScooterEnter.setEvent(CommonEvent.END.getValue());
            iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
            iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
            iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
            scooterIotService.navigation(iotScooterEnter);

            savrOrderTrace(enter, null, null,
                    deliveryDetail.getExpressDeliveryId(),
                    deliveryDetail.getExpressOrderId(),
                    corDriverScooter.getDriverId(),
                    corDriverScooter.getScooterId(),
                    corTenantScooter == null ? new BigDecimal(enter.getLng()) : corTenantScooter.getLongitule(),
                    corTenantScooter == null ? new BigDecimal(enter.getLat()) : corTenantScooter.getLatitude(),
                    ExpressOrderStatusEnums.REJECTED.getValue(),
                    ExpressOrderEventEnums.REJECTED.getValue(),
                    enter.getReason());

            // 个人信息
            QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
            CorUserProfile corUserProfile = corUserProfileService.getOne(corUserProfileQueryWrapper);

            Object[] args = new Object[]{corExpressOrder.getOrderNo(), corExpressDelivery.getId(),
                    new StringBuffer(corUserProfile.getFirstName()).append(" ").append(corUserProfile.getLastName()).toString(),
                    enter.getReason()};

            // 消息推送
            // 1、 app 自己
            PushMsgBo pushApp = PushMsgBo.builder()
                    .enter(enter)
                    .pushType(PlatformTypeEnums.ANDROID.getValue())
                    .status(ExpressOrderStatusEnums.REJECTED.getValue())
                    .args(args)
                    .bizType(MesageBizTypeEnum.EXPRESS_ORDER.getValue())
                    .bizId(enter.getId())
                    .belongId(enter.getUserId())
                    .appId(AppIDEnums.SAAS_APP.getAppId())
                    .systemId(AppIDEnums.SAAS_APP.getSystemId())
                    .pushType(PlatformTypeEnums.ANDROID.getValue())
                    .messagePriority(MessagePriorityEnums.NONE_REMIND.getValue())
                    .mesageType(MesageTypeEnum.SITE.getValue())
                    .build();
            pushMsg(pushApp);

            // 2 app----》web
            PushMsgBo pushWeb = PushMsgBo.builder()
                    .enter(enter)
                    .pushType(PlatformTypeEnums.PC.getValue())
                    .status(ExpressOrderStatusEnums.REJECTED.getValue())
                    .args(args)
                    .bizType(MesageBizTypeEnum.EXPRESS_ORDER.getValue())
                    .bizId(enter.getId())
                    .belongId(corExpressDelivery.getCreateBy())
                    .appId(AppIDEnums.SAAS_WEB.getAppId())
                    .systemId(AppIDEnums.SAAS_WEB.getSystemId())
                    .pushType(PlatformTypeEnums.PC.getValue())
                    .messagePriority(MessagePriorityEnums.FORCED_REMIND.getValue())
                    .mesageType(MesageTypeEnum.NONE.getValue())
                    .build();
            pushMsg(pushWeb);

            // 判断大订单是否完成 完成进行大订单推送
            if (taskPush) {
                PushMsgBo taskPushWeb = PushMsgBo.builder()
                        .enter(enter)
                        .status(TaskStatusEnums.DELIVERED.getValue())
                        .args(args)
                        .bizType(MesageBizTypeEnum.EXPRESS_DELIVERY.getValue())
                        .bizId(corExpressDelivery.getId())
                        .belongId(corExpressDelivery.getCreateBy())
                        .appId(AppIDEnums.SAAS_WEB.getAppId())
                        .systemId(AppIDEnums.SAAS_WEB.getSystemId())
                        .pushType(PlatformTypeEnums.PC.getValue())
                        .messagePriority(MessagePriorityEnums.FORCED_REMIND.getValue())
                        .mesageType(MesageTypeEnum.NONE.getValue())
                        .build();
                pushMsg(taskPushWeb);
            }
        } finally {
            //解锁
            jedisService.unlock(JedisConstant.JEDIS_KEY);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:41
     * @Param: enter
     * @Return: CompleteResult
     * @desc: 完成订单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CompleteResult complete(CompleteEnter enter) {
        //验证订单是否存在
        CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
        if (corExpressOrder == null) {
            throw new MobileBException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        // 状态过滤
        if (!StringUtils.equals(corExpressOrder.getStatus(), ExpressOrderStatusEnums.SHIPPING.getValue())) {
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 拒绝订单
        // 查询expressDeliveryDetail订单
        QueryWrapper<CorExpressDeliveryDetail> corExpressDeliveryDetailQueryWrapper = new QueryWrapper<>();
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_DR, 0);
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_TENANT_ID, enter.getTenantId());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_STATUS, ExpressDeliveryDetailStatusEnums.SHIPPING.getValue());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_EXPRESS_ORDER_ID, enter.getId());
        CorExpressDeliveryDetail deliveryDetail = corExpressDeliveryDetailService.getOne(corExpressDeliveryDetailQueryWrapper);

        deliveryDetail.setStatus(ExpressDeliveryDetailStatusEnums.COMPLETED.getValue());
        deliveryDetail.setAta(new Date());
        deliveryDetail.setUpdatedBy(enter.getUserId());
        deliveryDetail.setUpdatedTime(new Date());
        corExpressDeliveryDetailService.updateById(deliveryDetail);

        // 更新expressOrder 信息
        corExpressOrder.setStatus(ExpressOrderStatusEnums.COMPLETED.getValue());
        corExpressOrder.setUpdatedBy(enter.getUserId());
        corExpressOrder.setUpdatedTime(new Date());
        corExpressOrder.setDeliveredTime(new Date());
        corExpressOrderService.updateById(corExpressOrder);

        //维护delivery 中的 完成数量
        CorExpressDelivery corExpressDelivery = corExpressDeliveryService.getById(deliveryDetail.getExpressDeliveryId());
        int time = DateUtil.timeComolete(deliveryDetail.getAta(), deliveryDetail.getAtd()).intValue();
        corExpressDelivery.setDrivenDuration(time);
        Boolean taskComplete = Boolean.FALSE;
        corExpressDelivery.setOrderCompleteNum(corExpressDelivery.getOrderCompleteNum() + 1);
        if (corExpressDelivery.getOrderCompleteNum().equals(corExpressDelivery.getOrderSum())) {
            corExpressDelivery.setStatus(TaskStatusEnums.DELIVERED.getValue());
            corExpressDelivery.setDeliveryEndTime(new Date());

            //大订单完成进行 大订单推送
            taskComplete = Boolean.TRUE;
        }
        corExpressDeliveryService.updateById(corExpressDelivery);

        // 获取正在骑行的车辆记录
        CorDriverScooter corDriverScooter = edOrderServiceMapper.queryScooterIdByUserId(enter.getUserId(), enter.getTenantId());

        // 查询车辆信息
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, corDriverScooter.getScooterId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        CorTenantScooter corTenantScooter = corTenantScooterService.getOne(corTenantScooterQueryWrapper);

        /**
         * 完成订单 -- 结束导航
         */
//        ScooterNavigationDTO scooterNavigation = new ScooterNavigationDTO();
//        scooterNavigation.setEvent(CommonEvent.END.getValue());
//        scooterNavigation.setLat(enter.getLat());
//        scooterNavigation.setLng(enter.getLng());
//        // 现在暂时不确定本次导航所花时间和行驶距离数据来源
//        scooterNavigation.setMileage(null);
//        scooterNavigation.setDuration(null);
//
//        scooterEmqXService.scooterNavigation(scooterNavigation, corDriverScooter.getScooterId(), getUserServiceType(enter));

        // 结束导航
        IotScooterEnter iotScooterEnter = new IotScooterEnter();
        BeanUtils.copyProperties(enter, iotScooterEnter);
        iotScooterEnter.setId(corDriverScooter.getScooterId());
        iotScooterEnter.setEvent(CommonEvent.END.getValue());
        iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
        iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
        iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
        scooterIotService.navigation(iotScooterEnter);

        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
        CorUserProfile corUserProfile = corUserProfileService.getOne(corUserProfileQueryWrapper);

        Object[] args = new Object[]{corExpressOrder.getOrderNo(), corExpressDelivery.getId(),
                new StringBuffer(corUserProfile.getFirstName()).append(" ").append(corUserProfile.getLastName()).toString()};


        // 订单日志
        savrOrderTrace(enter, enter.getLng(),
                enter.getLat(),
                deliveryDetail.getExpressDeliveryId(),
                deliveryDetail.getExpressOrderId(),
                corDriverScooter.getDriverId(),
                corDriverScooter.getScooterId(),
                corTenantScooter == null ? new BigDecimal(enter.getLng()) : corTenantScooter.getLongitule(),
                corTenantScooter == null ? new BigDecimal(enter.getLat()) : corTenantScooter.getLatitude(),
                ExpressOrderStatusEnums.COMPLETED.getValue(),
                ExpressOrderEventEnums.COMPLETED.getValue(), null
        );
        // 消息推送
        // app自己
        PushMsgBo pushApp = PushMsgBo.builder()
                .enter(enter)
                .status(ExpressOrderStatusEnums.COMPLETED.getValue())
                .args(args)
                .bizType(MesageBizTypeEnum.EXPRESS_ORDER.getValue())
                .bizId(enter.getId())
                .belongId(enter.getUserId())
                .appId(enter.getAppId())
                .systemId(enter.getSystemId())
                .pushType(PlatformTypeEnums.ANDROID.getValue())
                .appId(AppIDEnums.SAAS_APP.getAppId())
                .systemId(AppIDEnums.SAAS_APP.getSystemId())
                .messagePriority(MessagePriorityEnums.NONE_REMIND.getValue())
                .mesageType(MesageTypeEnum.SITE.getValue())
                .build();
        pushMsg(pushApp);
        // app-web
        PushMsgBo pushWeb = PushMsgBo.builder()
                .enter(enter)
                .status(ExpressOrderStatusEnums.COMPLETED.getValue())
                .args(args)
                .bizType(MesageBizTypeEnum.EXPRESS_ORDER.getValue())
                .bizId(enter.getId())
                .belongId(corExpressDelivery.getCreateBy())
                .appId(AppIDEnums.SAAS_WEB.getAppId())
                .systemId(AppIDEnums.SAAS_WEB.getSystemId())
                .pushType(PlatformTypeEnums.PC.getValue())
                .messagePriority(MessagePriorityEnums.COMMON_REMIND.getValue())
                .mesageType(MesageTypeEnum.NONE.getValue())
                .build();
        pushMsg(pushWeb);

        // 判断大订单是否完成 完成进行大订单推送
        if (taskComplete) {
            PushMsgBo taskPushWeb = PushMsgBo.builder()
                    .enter(enter)
                    .status(TaskStatusEnums.DELIVERED.getValue())
                    .args(args)
                    .bizType(MesageBizTypeEnum.EXPRESS_DELIVERY.getValue())
                    .bizId(corExpressDelivery.getId())
                    .belongId(corExpressDelivery.getCreateBy())
                    .appId(AppIDEnums.SAAS_WEB.getAppId())
                    .systemId(AppIDEnums.SAAS_WEB.getSystemId())
                    .pushType(PlatformTypeEnums.PC.getValue())
                    .messagePriority(MessagePriorityEnums.FORCED_REMIND.getValue())
                    .mesageType(MesageTypeEnum.NONE.getValue())
                    .build();
            pushMsg(taskPushWeb);
        }
        return CompleteResult.builder()
                .avg("1")
                .co2("2")
                .mileage("3")
                .money("4")
                .build();
    }


    private void savrOrderTrace(GeneralEnter enter, String lng, String lat, Long deliveryId, Long orderId, Long driverId, Long scooterId, BigDecimal scooterLng, BigDecimal scooterLat, String status,
                                String event, String reason) {

        BaseExpressOrderTraceEnter baseExpressOrderTraceEnter = new BaseExpressOrderTraceEnter();
        BeanUtils.copyProperties(enter, baseExpressOrderTraceEnter);
        baseExpressOrderTraceEnter.setId(idAppService.getId(SequenceName.COR_EXPRESS_ORDER_TRACE));
        baseExpressOrderTraceEnter.setDr(0);
        baseExpressOrderTraceEnter.setExpressDeliveryId(deliveryId);
        baseExpressOrderTraceEnter.setExpressOrderId(orderId);
        baseExpressOrderTraceEnter.setTenantId(enter.getTenantId());
        baseExpressOrderTraceEnter.setDriverId(driverId);
        baseExpressOrderTraceEnter.setStatus(status);
        baseExpressOrderTraceEnter.setEvent(event);
        baseExpressOrderTraceEnter.setReason(reason);
        baseExpressOrderTraceEnter.setEventTime(new Date());
        baseExpressOrderTraceEnter.setLongitude(new BigDecimal(StringUtils.isNotBlank(lng) ? lng : "0"));
        baseExpressOrderTraceEnter.setLatitude(new BigDecimal(StringUtils.isNotBlank(lat) ? lat : "0"));
        baseExpressOrderTraceEnter.setGeohash(MapUtil.geoHash(StringUtils.isNotBlank(lng) ? lng : "0", StringUtils.isNotBlank(lat) ? lat : "0"));
        baseExpressOrderTraceEnter.setScooterId(scooterId);
        baseExpressOrderTraceEnter.setScooterLatitude(scooterLat);
        baseExpressOrderTraceEnter.setScooterLongitude(scooterLng);
        baseExpressOrderTraceEnter.setScooterGeohash(MapUtil.geoHash(scooterLng.toString(), scooterLat.toString()));
        baseExpressOrderTraceEnter.setCreatedBy(enter.getUserId());
        baseExpressOrderTraceEnter.setCreatedTime(new Date());
        baseExpressOrderTraceEnter.setUpdatedBy(enter.getUserId());
        baseExpressOrderTraceEnter.setUpdatedTime(new Date());
        edOrderDeliveryTraceService.saveExpressOrderTrace(baseExpressOrderTraceEnter);
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
        pushParameter.put("messagePriority", SesStringUtils.isEmpty(pushMsg.getMessagePriority()) == true ? MessagePriorityEnums.NONE_REMIND.getValue() :
                pushMsg.getMessagePriority());
        pushParameter.put("mesageType", SesStringUtils.isEmpty(pushMsg.getMesageType()) == true ? MesageTypeEnum.NONE.getValue() : pushMsg.getMesageType());


        pushParameter.put("generalEnter", generalEnter);

        // 消息所推对象参数
        pushParameter.put("userIds", String.valueOf(pushMsg.getBelongId()));
        pushParameter.put("createUser", pushMsg.getEnter().getUserId().toString());
        pushParameter.put("appId", pushMsg.getAppId());
        pushParameter.put("systemId", pushMsg.getSystemId());
        pushParameter.put("pushType", pushMsg.getPushType());
        pushService.pushMessage(JSON.toJSONString(pushParameter));
    }

    /**
     * 获取用户业务类型 1-2B 2-2C
     *
     * @param enter
     * @return
     */
    private int getUserServiceType(GeneralEnter enter) {
        int userServiceType = 0;

        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (!AccountTypeEnums.WEB_REPAIR.getAccountType().equals(queryUserResult.getUserType())
                || !AccountTypeEnums.APP_PERSONAL.getAccountType().equals(queryUserResult.getUserType())) {
            userServiceType = 1;
        } else if (AccountTypeEnums.APP_PERSONAL.getAccountType().equals(queryUserResult.getUserType())) {
            userServiceType = 2;
        }

        return userServiceType;
    }

}
