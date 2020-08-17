package com.redescooter.ses.service.mobile.b.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.BizType;
import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryResultEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.mesage.MesageTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MessagePriorityEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PlatformTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.delivery.BaseDeliveryEnter;
import com.redescooter.ses.api.common.vo.message.PushMsgBo;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.foundation.service.PushService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.DeliveryService;
import com.redescooter.ses.api.mobile.b.service.DeliveryTraceService;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryDetailResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListEnter;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListResult;
import com.redescooter.ses.api.mobile.b.vo.RefuseEnter;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryTraceEnter;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.b.dao.DeliveryServiceMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDeliveryMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDeliveryTraceMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorUserProfileMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDelivery;
import com.redescooter.ses.service.mobile.b.dm.base.CorDeliveryTrace;
import com.redescooter.ses.service.mobile.b.dm.base.CorUserProfile;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.tool.utils.CO2MoneyConversionUtil;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.StatisticalUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName:DeliveryList
 * @description: DeliveryList
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 14:38
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {


    @Autowired
    private CorDeliveryMapper corDeliveryMapper;

    @Autowired
    private DeliveryServiceMapper deliveryServiceMapper;

    @Autowired
    private DeliveryTraceService deliveryTraceService;
    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private CorDeliveryTraceMapper corDeliveryTraceMapper;

    @Reference
    private TenantBaseService tenantBaseService;

    @Reference
    private ScooterService scooterService;

    @Reference
    private ScooterIotService scooterIotService;

    @Reference
    private PushService pushService;

    // 创建 开始订单锁
    private Lock startDeliveryLock = new ReentrantLock();
    // 拒绝 开始订单锁
    private Lock refuseDeliveryLock = new ReentrantLock();
    // 完成 开始订单锁
    private Lock completeDeliveryLock = new ReentrantLock();


    /**
     * delviery 列表
     *
     * @param enter
     * @return
     */
    @Override

    public DeliveryListResult list(DeliveryListEnter enter) {

        // 查询订单状态及数量统计
        List<CountByStatusResult> list = deliveryServiceMapper.doingCountByStatus(enter);

        int count = deliveryServiceMapper.refuseDelivery(enter.getUserId(), DeliveryStatusEnums.REJECTED.getValue());
        list.add(CountByStatusResult.builder().status(DeliveryStatusEnums.REJECTED.getValue()).totalCount(count).build());

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : list) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (DeliveryStatusEnums status : DeliveryStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }

        List<DeliveryDetailResult> deliveryList = deliveryServiceMapper.deliveryList(enter);
        QueryTenantResult queryTenantResult = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        List<Long> scooterIdList = new ArrayList<>();
        deliveryList.forEach(item -> {
            Optional.ofNullable(item).ifPresent(it -> {
                scooterIdList.add(it.getScooterId());
                item.setTenantLongitude(queryTenantResult.getLongitude());
                item.setTenantLatitude(queryTenantResult.getLatitude());
            });
        });

        // 查询车辆信息
        List<BaseScooterResult> scooter = scooterService.scooterInfor(scooterIdList);
        scooter.forEach(item -> {
            Optional.ofNullable(item).ifPresent(it -> {
                for (DeliveryDetailResult delivery : deliveryList) {
                    if (it.getId().equals(delivery.getScooterId())) {
                        delivery.setScooterId(it.getId());
                        delivery.setScooterLongitude(it.getLongitule());
                        delivery.setScooterLatitude(it.getLatitude());
                        continue;
                    }
                }
            });
        });

        return new DeliveryListResult(map, deliveryList);
    }

    /**
     * detail
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryDetailResult detail(IdEnter enter) {
        CorDelivery delivery = corDeliveryMapper.selectById(enter.getId());
        if (delivery == null) {
            throw new MobileBException(ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getMessage());
        }

        DeliveryDetailResult result = new DeliveryDetailResult();
        BeanUtils.copyProperties(delivery, result);

        // 租户信息
        QueryTenantResult tenant = tenantBaseService.queryTenantById(IdEnter.builder().id(delivery.getTenantId()).build());

        // 车辆信息
        List<Long> scooterId = new ArrayList<>();
        scooterId.add(delivery.getScooterId());
        List<BaseScooterResult> scooter = scooterService.scooterInfor(scooterId);

        QueryWrapper<CorDeliveryTrace> corDeliveryTraceQueryWrapper = new QueryWrapper<>();
        corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_DELIVERY_ID, delivery.getId());
        corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_STATUS, DeliveryStatusEnums.REJECTED.getValue());
        CorDeliveryTrace deliveryTrace = corDeliveryTraceMapper.selectOne(corDeliveryTraceQueryWrapper);
        if (deliveryTrace != null) {
            result.setReason(deliveryTrace.getReason());
        }

        result.setTenantLatitude(tenant.getLatitude());
        result.setTenantLongitude(tenant.getLongitude());
        if (scooter.size() > 0) {
            result.setScooterLatitude(scooter.get(0).getLatitude());
            result.setScooterLongitude(scooter.get(0).getLongitule());
            result.setBattery(scooter.get(0).getBattery());
        }
        return result;
    }

    /**
     * 开始
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult start(StartEnter enter) {
        // 上锁
//        startDeliveryLock.lock();
//        try {

        // 查询 该用户是否有配送中的订单
        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.eq(CorDelivery.COL_STATUS, DeliveryStatusEnums.DELIVERING.getValue());
        corDeliveryQueryWrapper.eq(CorDelivery.COL_DELIVERER_ID, enter.getUserId());
        Integer count = corDeliveryMapper.selectCount(corDeliveryQueryWrapper);
        if (count > 0) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getCode(), ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getMessage());
        }


        CorDelivery delivery = corDeliveryMapper.selectById(enter.getId());
        if (delivery == null) {
            throw new MobileBException(ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(DeliveryStatusEnums.PENDING.getValue(), delivery.getStatus())) {
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 开启导航
        navugation(enter.getBluetoothCommunication(), enter.getLat(), enter.getLng(), enter, delivery, CommonEvent.START.getValue());
        // 修改状态
        delivery.setId(enter.getId());
        delivery.setStatus(DeliveryStatusEnums.DELIVERING.getValue());
        delivery.setAtd(new Date());
        delivery.setDrivenMileage(new BigDecimal(enter.getMileage()));
        delivery.setUpdatedBy(enter.getUserId());
        delivery.setUpdatedTime(new Date());
        corDeliveryMapper.updateById(delivery);
        // 记录日志
        saveDelivertTrace(enter, delivery, DeliveryEventEnums.START.getValue());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            startDeliveryLock.unlock();
//        }
        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, delivery.getDelivererId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        if (corUserProfile == null) {
            throw new MobileBException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        String[] args = new String[]{delivery.getOrderNo(), new StringBuilder().append(corUserProfile.getFirstName() + " " + corUserProfile.getLastName()).toString()};
        // 消息推送
        PushMsgBo pushMsg = PushMsgBo.builder()
                .enter(enter)
                .pushType(PlatformTypeEnums.PC.getValue())
                .bizId(delivery.getId())
                .bizType(BizType.DELIVERY.getValue())
                .status(DeliveryStatusEnums.DELIVERING.getValue())
                .args(args)
                .belongId(delivery.getCreatedBy())
                .systemId(AppIDEnums.SAAS_WEB.getSystemId())
                .appId(AppIDEnums.SAAS_WEB.getAppId())
                .messagePriority(MessagePriorityEnums.COMMON_REMIND.getValue())
                .mesageType(MesageTypeEnum.NONE.getValue())
                .build();
        pushMsg(pushMsg);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 拒绝
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult refuse(RefuseEnter enter) {

        // 查询 该用户是否有配送中的订单
        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.eq(CorDelivery.COL_STATUS, DeliveryStatusEnums.DELIVERING.getValue());
        corDeliveryQueryWrapper.eq(CorDelivery.COL_DELIVERER_ID, enter.getUserId());
        Integer count = corDeliveryMapper.selectCount(corDeliveryQueryWrapper);
        if (count > 0) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getCode(), ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getMessage());
        }

        CorDelivery delivery = corDeliveryMapper.selectById(enter.getId());
        if (delivery == null) {
            throw new MobileBException(ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(DeliveryStatusEnums.PENDING.getValue(), delivery.getStatus())) {
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 修改订单状态
        delivery.setStatus(DeliveryStatusEnums.REJECTED.getValue());
        delivery.setUpdatedBy(enter.getUserId());
        delivery.setUpdatedTime(new Date());
        corDeliveryMapper.updateById(delivery);

        // 订单日志
        saveDelivertTrace(enter, delivery, DeliveryEventEnums.REJECT.getValue());

        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, delivery.getDelivererId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        if (corUserProfile == null) {
            throw new MobileBException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        String[] args = new String[]{delivery.getOrderNo(), new StringBuilder().append(corUserProfile.getFirstName() + " " + corUserProfile.getLastName()).toString(), enter.getReason()};
        // 消息推送
        PushMsgBo pushMsg = PushMsgBo.builder()
                .enter(enter)
                .pushType(PlatformTypeEnums.PC.getValue())
                .bizId(delivery.getId())
                .bizType(BizType.DELIVERY.getValue())
                .status(DeliveryStatusEnums.REJECTED.getValue())
                .args(args)
                .belongId(delivery.getCreatedBy())
                .systemId(AppIDEnums.SAAS_WEB.getSystemId())
                .appId(AppIDEnums.SAAS_WEB.getAppId())
                .messagePriority(MessagePriorityEnums.FORCED_REMIND.getValue())
                .mesageType(MesageTypeEnum.NONE.getValue())
                .build();
        pushMsg(pushMsg);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 完成
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public CompleteResult complete(CompleteEnter enter) {
        List<String> deliveryStatus = new ArrayList<>();
        deliveryStatus.add(DeliveryStatusEnums.DELIVERING.getValue());

        CorDelivery delivery = corDeliveryMapper.selectById(enter.getId());
        if (delivery == null) {
            throw new MobileBException(ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getMessage());
        }

        if (!deliveryStatus.contains(delivery.getStatus())) {
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 关闭导航
        navugation(enter.getBluetoothCommunication(), enter.getLat(), enter.getLng(), enter, delivery, CommonEvent.END.getValue());
        // 修改状态
        delivery.setAta(new Date());
        delivery.setDrivenDuration(DateUtil.timeComolete(delivery.getAtd(), delivery.getAta()).intValue());
        delivery.setCo2(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(delivery.getDrivenMileage().longValue())));
        delivery.setSavings(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(delivery.getDrivenMileage().longValue())));

        //TODO  完成时报超时完成
        String deliveryResult = DateUtil.timeComolete(delivery.getEta(), delivery.getAta()).intValue() > 0 ? DeliveryResultEnums.DELAY.getValue() : DeliveryResultEnums.ONTIME.getValue();
        delivery.setResult(deliveryResult);
        delivery.setStatus(StringUtils.equals(DeliveryResultEnums.DELAY.getValue(), deliveryResult) ? DeliveryStatusEnums.TIMEOUT_COMPLETE.getValue() : DeliveryStatusEnums.COMPLETED.getValue());
        delivery.setUpdatedBy(enter.getUserId());
        delivery.setUpdatedTime(new Date());
        corDeliveryMapper.updateById(delivery);

        // 记录日志
        saveDelivertTrace(enter, delivery, StringUtils.equals(delivery.getStatus(), DeliveryStatusEnums.TIMEOUT_COMPLETE.getValue()) == true ? DeliveryEventEnums.TIMEOUT_COMPLETE.getValue() :
                DeliveryEventEnums.COMPLETED.getValue());

        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, delivery.getDelivererId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        if (corUserProfile == null) {
            throw new MobileBException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        String[] args = new String[]{delivery.getOrderNo()};
        // 消息推送
        PushMsgBo pushMsg = PushMsgBo.builder()
                .enter(enter)
                .pushType(PlatformTypeEnums.PC.getValue())
                .bizId(delivery.getId())
                .bizType(BizType.DELIVERY.getValue())
                .status(delivery.getStatus())
                .args(args)
                .belongId(delivery.getCreatedBy())
                .systemId(AppIDEnums.SAAS_WEB.getSystemId())
                .appId(AppIDEnums.SAAS_WEB.getAppId())
                .messagePriority(MessagePriorityEnums.COMMON_REMIND.getValue())
                .mesageType(MesageTypeEnum.NONE.getValue())
                .build();
        pushMsg(pushMsg);

        return new CompleteResult(delivery.getDrivenMileage().toString(),
                StatisticalUtil.percentageUtil(delivery.getDrivenMileage().intValue(), delivery.getDrivenDuration().intValue() > 0 ? delivery.getDrivenDuration().intValue() : 1, 2)
                , delivery.getCo2().toString()
                , delivery.getSavings().toString());
    }

    /**
     * 导航
     *
     * @param bluetoothCommunication
     * @param lat
     * @param lon
     * @param enter
     * @param delivery
     */
    private void navugation(Boolean bluetoothCommunication, String lat, String lon, GeneralEnter enter, CorDelivery delivery, String event) {
        IotScooterEnter naviagtionEnter = IotScooterEnter.builder()
                .bluetoothCommunication(bluetoothCommunication)
                .latitude(new BigDecimal(lat))
                .longitude(new BigDecimal(lon))
                .id(delivery.getScooterId())
                .event(event)
                .build();
        BeanUtils.copyProperties(enter, naviagtionEnter);
        naviagtionEnter.setId(delivery.getScooterId());
        scooterIotService.navigation(naviagtionEnter);
    }

    /**
     * 日志
     *
     * @param enter
     * @param delivery
     * @param deliveryEvent
     */
    private void saveDelivertTrace(GeneralEnter enter, CorDelivery delivery, String deliveryEvent) {
        BaseDeliveryEnter baseDeliveryEnter = new BaseDeliveryEnter();
        BeanUtils.copyProperties(enter, baseDeliveryEnter);
        BeanUtils.copyProperties(delivery, baseDeliveryEnter);
        SaveDeliveryTraceEnter saveDeliveryTraceEnter = SaveDeliveryTraceEnter.builder()
                .event(deliveryEvent)
                .build();
        BeanUtils.copyProperties(enter, saveDeliveryTraceEnter);
        saveDeliveryTraceEnter.setT(baseDeliveryEnter);
        List<SaveDeliveryTraceEnter<BaseDeliveryEnter>> saveDeliveryTraceEnterList = new ArrayList<>();
        saveDeliveryTraceEnterList.add(saveDeliveryTraceEnter);
        deliveryTraceService.saveDeliveryTrace(saveDeliveryTraceEnterList);
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
        pushParameter.put("messagePriority", StringUtils.isEmpty(pushMsg.getMessagePriority()) == true ? MessagePriorityEnums.NONE_REMIND.getValue() :
                pushMsg.getMessagePriority());
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
