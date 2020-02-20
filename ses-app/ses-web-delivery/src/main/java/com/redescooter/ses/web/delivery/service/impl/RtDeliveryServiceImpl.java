package com.redescooter.ses.web.delivery.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.BizType;
import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.driver.DriverStatusEnum;
import com.redescooter.ses.api.common.enums.jiguang.PlatformTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MesageTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MessagePriorityEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.message.PushMsgBo;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.PushService;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.foundation.vo.tenant.TenantConfigInfoResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.OrderDeliveryServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.CorUserProfileMapper;
import com.redescooter.ses.web.delivery.dm.CorDelivery;
import com.redescooter.ses.web.delivery.dm.CorDeliveryTrace;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import com.redescooter.ses.web.delivery.dm.CorUserProfile;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.RtDeliveryService;
import com.redescooter.ses.web.delivery.service.base.CorDeliveryService;
import com.redescooter.ses.web.delivery.service.base.CorDeliveryTraceService;
import com.redescooter.ses.web.delivery.service.base.CorDriverScooterService;
import com.redescooter.ses.web.delivery.service.base.CorDriverService;
import com.redescooter.ses.web.delivery.vo.ClosedEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryDetailsResult;
import com.redescooter.ses.web.delivery.vo.DeliveryMapResult;
import com.redescooter.ses.web.delivery.vo.DeliveryNodeResult;
import com.redescooter.ses.web.delivery.vo.DeliveryResetEnter;
import com.redescooter.ses.web.delivery.vo.DriverDeliveryInfoResult;
import com.redescooter.ses.web.delivery.vo.ListDeliveryPage;
import com.redescooter.ses.web.delivery.vo.ListDeliveryResult;
import com.redescooter.ses.web.delivery.vo.MapEnter;
import com.redescooter.ses.web.delivery.vo.MapResult;
import com.redescooter.ses.web.delivery.vo.SaveOrderDeliveryEnter;
import com.redescooter.ses.web.delivery.vo.ScooterLicensePlateEnter;
import com.redescooter.ses.web.delivery.vo.ScooterLicensePlateResult;
import com.redescooter.ses.web.delivery.vo.ScooterMapResult;
import com.redescooter.ses.web.delivery.vo.SelectDriverResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 3:42 下午
 * @ClassName: RtDeliveryServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class RtDeliveryServiceImpl implements RtDeliveryService {

    @Autowired
    private OrderDeliveryServiceMapper orderDeliveryServiceMapper;
    @Autowired
    private CorDeliveryService deliveryService;
    @Autowired
    private CorDriverScooterService driverScooterService;
    @Autowired
    private CorDeliveryTraceService deliveryTraceService;
    @Autowired
    private CorDriverService driverService;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

    @Reference
    private TenantBaseService tenantBaseService;
    @Reference
    private IdAppService idAppService;
    @Reference
    private GenerateService generateService;
    @Reference
    private ScooterService scooterService;
    @Reference
    private PushService pushService;

    /**
     * 创建配送单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SaveOrderDeliveryEnter enter) {
        //系统默认30分钟
        String plannedTime = enter.getAppointment() == null ? "30" : enter.getAppointment();
        String timeoutExpectde = enter.getTimeoutExpectde() == null ? "15" : enter.getTimeoutExpectde();
        int timeDifference = Integer.parseInt(plannedTime) - Integer.parseInt(timeoutExpectde);

        if (enter.getDriverId() == null || enter.getDriverId() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.DRIVER_CANNOT_EMPTY.getMessage());
        }
        if (enter.getParcelQuantity() == null || enter.getParcelQuantity() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.PACKAGES_CANNOT_BE_EMPTY.getCode(), ExceptionCodeEnums.PACKAGES_CANNOT_BE_EMPTY.getMessage());
        }
        if (StringUtils.isAnyBlank(enter.getRecipient(), enter.getRecipientAddress(), enter.getRecipientTel(), enter.getCountryCode())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.RECIPIENT_INFORMATION_IS_MISSING.getCode(), ExceptionCodeEnums.RECIPIENT_INFORMATION_IS_MISSING.getMessage());
        }

        if (StringUtils.isAnyBlank(enter.getLatitude(), enter.getLongitude())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.MISSING_LATITUDE_AND_LONGITUDE.getCode(), ExceptionCodeEnums.MISSING_LATITUDE_AND_LONGITUDE.getMessage());
        }
        //创建配送单
        QueryTenantResult tenant = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        QueryWrapper<CorDriverScooter> wrapper = new QueryWrapper<>();
        wrapper.eq(CorDriverScooter.COL_DR, 0);
        wrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getDriverId());
        wrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        wrapper.eq(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
        CorDriverScooter driverScooter = driverScooterService.getOne(wrapper);
        if (driverScooter == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getCode(), ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getMessage());
        }
        if (enter.getId() == null || enter.getId() == 0) {

            CorDriver driver = driverService.getById(enter.getDriverId());

            if (driver == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
            }
            // 获取店铺配置
            TenantConfigInfoResult tenantConfigInfoResult = tenantBaseService.tenantConfigInfo(enter);


            CorDelivery deliverySave = new CorDelivery();
            BeanUtils.copyProperties(enter, deliverySave);
            deliverySave.setId(idAppService.getId(SequenceName.COR_DELIVERY));
            deliverySave.setDr(0);
            deliverySave.setDelivererId(driver.getUserId());
            deliverySave.setOrderNo(generateService.getOrderNo());
            deliverySave.setLatitude(new BigDecimal(enter.getLatitude()));
            deliverySave.setLongitude(new BigDecimal(enter.getLongitude()));
            deliverySave.setGeohash(MapUtil.geoHash(enter.getLongitude(), enter.getLatitude()));
            deliverySave.setStatus(DeliveryStatusEnums.PENDING.getValue());
            // todo 预计开始配送的时间,默认十分钟后开始配送
            deliverySave.setEtd(DateUtils.addMinutes(new Date(), Integer.parseInt("10")));
            deliverySave.setEta(DateUtils.addMinutes(new Date(), tenantConfigInfoResult.getEstimatedDuration().intValue()/60));
            BigDecimal drivenMileage = new BigDecimal(MapUtil.getDistance(enter.getLatitude(), enter.getLongitude(), tenant.getLatitude() == null ? "0" : String.valueOf(tenant.getLatitude()),
                    tenant.getLongitude() == null ? "0" : String.valueOf(tenant.getLongitude())));
            deliverySave.setDrivenMileage(drivenMileage);
            deliverySave.setScooterId(driverScooter.getScooterId());
            deliverySave.setTimeoutExpectde(enter.getTimeoutExpectde());
            //配送时长减去提前预警时长,设置超时预警时间
            deliverySave.setTimeOut(DateUtils.addMinutes(new Date(), timeDifference));
            deliverySave.setPlannedTime(plannedTime);
            deliverySave.setCreatedBy(enter.getUserId());
            deliverySave.setCreatedTime(new Date());
            deliverySave.setUpdatedBy(enter.getUserId());
            deliverySave.setUpdatedTime(new Date());
            deliveryService.save(deliverySave);

            saveDeliveryNode(deliverySave, enter, null, statusConversionEvent(deliverySave.getStatus()));

        } else {
            //编辑配送单
            CorDelivery oloDelivery = deliveryService.getById(enter.getId());

            BeanUtils.copyProperties(enter, oloDelivery);

            CorDriver driver = driverService.getById(enter.getDriverId());
            if (driver == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
            }
            oloDelivery.setScooterId(driverScooter.getScooterId());
            oloDelivery.setLatitude(new BigDecimal(enter.getLatitude()));
            oloDelivery.setLongitude(new BigDecimal(enter.getLongitude()));
            oloDelivery.setGeohash(MapUtil.geoHash(enter.getLongitude(), enter.getLatitude()));
            oloDelivery.setTimeoutExpectde(enter.getTimeoutExpectde());
            //设置超时预警时间
            oloDelivery.setTimeOut(DateUtils.addMinutes(new Date(), timeDifference));
            oloDelivery.setPlannedTime(plannedTime);
            oloDelivery.setUpdatedBy(enter.getUserId());
            oloDelivery.setUpdatedTime(new Date());
            deliveryService.updateById(oloDelivery);
        }

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 配送单状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {

        List<CountByStatusResult> countByStatusResults = orderDeliveryServiceMapper.countStatus(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResults) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (DeliveryStatusEnums status : DeliveryStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 订单分页查询
     *
     * @param page
     * @return
     */
    @Override
    public PageResult<ListDeliveryResult> list(ListDeliveryPage page) {

        int totalRows = orderDeliveryServiceMapper.listCount(page);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(page);
        }

        List<ListDeliveryResult> list = orderDeliveryServiceMapper.list(page);

        return PageResult.create(page, totalRows, list);

    }

    /**
     * 配送单详情
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryDetailsResult details(IdEnter enter) {

        if (enter.getId() == null || enter.getId() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }

        DeliveryDetailsResult details = orderDeliveryServiceMapper.details(enter);

        if (details == null) {
            return new DeliveryDetailsResult();
        }

        List<Long> scooterId = new ArrayList<>();
        scooterId.add(details.getScooterId());
        List<BaseScooterResult> scooter = scooterService.scooterInfor(scooterId);

        QueryTenantResult queryTenantResult = tenantBaseService.queryTenantById(new IdEnter(details.getTenantId()));
        details.setTenantLat(queryTenantResult.getLatitude().toString());
        details.setTenantLng(queryTenantResult.getLongitude().toString());
        if(scooter.size()>0){
            details.setBattery(String.valueOf(scooter.get(0).getBattery()));
        }
        return details;
    }

    /**
     * @param enter
     * @return
     */
    @Override
    public List<SelectDriverResult> selectDriverList(GeneralEnter enter) {
        return orderDeliveryServiceMapper.selectDriverList(enter);
    }

    /**
     * 关闭订单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult closed(ClosedEnter enter) {
        if (enter.getId() == null || enter.getId() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }
        CorDelivery delivery = deliveryService.getById(enter.getId());

        if (!delivery.getStatus().equals(DeliveryStatusEnums.PENDING.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ORDER_HAS_STARTED_AND_CANNOT_BE_CANCELLED.getCode(), ExceptionCodeEnums.ORDER_HAS_STARTED_AND_CANNOT_BE_CANCELLED.getMessage());
        }

        delivery.setStatus(DeliveryStatusEnums.CANCEL.getValue());
        delivery.setUpdatedBy(enter.getUserId());
        delivery.setUpdatedTime(new Date());

        deliveryService.updateById(delivery);

        saveDeliveryNode(delivery, enter, enter.getReason(), statusConversionEvent(delivery.getStatus()));

        jedisCluster.set(enter.getId().toString(), JSON.toJSONString(delivery));
        jedisCluster.expire(enter.getId().toString(), new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());

        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        String[] args = new String[]{delivery.getOrderNo(), new StringBuilder().append(corUserProfile.getFirstName() + " " + corUserProfile.getLastName()).toString()};

        PushMsgBo pushMsg = PushMsgBo.builder()
                .enter(enter)
                .pushType(PlatformTypeEnum.ANDROID.getValue())
                .bizId(delivery.getId())
                .bizType(BizType.DELIVERY.getValue())
                .status(DeliveryStatusEnums.CANCEL.getValue())
                .args(args)
                .belongId(delivery.getDelivererId())
                .systemId(AppIDEnums.SAAS_APP.getSystemId())
                .appId(AppIDEnums.SAAS_APP.getAppId())
                .messagePriority(MessagePriorityEnums.NONE_REMIND.getValue())
                .mesageType(MesageTypeEnum.SITE.getValue())
                .build();
        pushMsg(pushMsg);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 订单地址
     *
     * @param enter
     * @return
     */
    @Override
    public MapResult map(MapEnter enter) {
        // 查询门店信息
        QueryTenantResult tenant = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        if (tenant == null) {
            return new MapResult();
        }
        // 司机车辆分配数据
        List<ScooterMapResult> scooterMapList = orderDeliveryServiceMapper.scooterMap(enter);

        List<CorDelivery> deliveryList = orderDeliveryServiceMapper.mapDeliveryList(enter);

        List<DeliveryMapResult> deliveryMapResultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            deliveryList.forEach(item -> {
                DeliveryMapResult delivery = DeliveryMapResult.builder()
                        .id(item.getId())
                        .status(item.getStatus())
                        .lng(item.getLongitude().toString())
                        .lat(item.getLatitude().toString())
                        .orderNo(item.getOrderNo())
                        .eta(item.getEta())
                        .parcelQuantity(item.getParcelQuantity())
                        .recipient(item.getRecipient())
                        .recipientAddress(item.getRecipientAddress())
                        .recipientTel(item.getRecipientTel())
                        .build();
                deliveryMapResultList.add(delivery);
            });
        }
        return MapResult.builder()
                .tenantId(tenant.getId())
                .tenantLat(tenant.getLatitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLatitude()))
                .tenantLng(tenant.getLongitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLongitude()))
                .scooterMapResultList(scooterMapList)
                .deliveryMapList(deliveryMapResultList)
                .build();
    }


    /**
     * 车牌号列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ScooterLicensePlateResult> scooterLicensePlate(ScooterLicensePlateEnter enter) {
        return orderDeliveryServiceMapper.scooterLicensePlateList(enter);
    }

    /**
     * 订单信息
     *
     * @param enter
     * @return
     */
    @Override
    public DriverDeliveryInfoResult driverDeliveryInfor(IdEnter enter) {

        DriverDeliveryInfoResult result = orderDeliveryServiceMapper.driverDeliveryInfor(enter);

        if (result == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getCode(), ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getMessage());
        }
        // 车辆数据
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(result.getScooterId());
        List<BaseScooterResult> scooterListResult = scooterService.scooterInfor(scooterIdList);

        //订单数据
        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.eq(CorDelivery.COL_DR, 0);
        corDeliveryQueryWrapper.eq(CorDelivery.COL_TENANT_ID, enter.getTenantId());
        corDeliveryQueryWrapper.eq(CorDelivery.COL_DELIVERER_ID, result.getUserId());
        corDeliveryQueryWrapper.in(CorDelivery.COL_STATUS, DeliveryStatusEnums.DELIVERING.getValue(), DeliveryStatusEnums.PENDING.getValue());
        List<CorDelivery> deliveryList = deliveryService.list(corDeliveryQueryWrapper);

        List<DeliveryMapResult> deliveryMapResultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            deliveryList.forEach(item -> {
                DeliveryMapResult delivery = DeliveryMapResult.builder()
                        .id(item.getId())
                        .status(item.getStatus())
                        .lng(item.getLongitude().toString())
                        .lat(item.getLatitude().toString())
                        .orderNo(item.getOrderNo())
                        .eta(item.getEta())
                        .parcelQuantity(item.getParcelQuantity())
                        .recipient(item.getRecipient())
                        .recipientAddress(item.getRecipientAddress())
                        .recipientTel(item.getRecipientTel())
                        .build();
                deliveryMapResultList.add(delivery);
            });
        }

        return DriverDeliveryInfoResult.builder()
                .scooterId(scooterListResult.get(0).getId())
                .licensePlate(scooterListResult.get(0).getLicensePlate())
                .battery(scooterListResult.get(0).getBattery())
                .deliveryMapResultList(deliveryMapResultList)
                .build();
    }

    /**
     * 车辆信息
     *
     * @param enter
     * @return
     */
    @Override
    public ScooterMapResult scooterInfor(IdEnter enter) {
        if (null == enter.getId() || 0 == enter.getId()) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ID_IS_EMPTY.getCode(), ExceptionCodeEnums.ID_IS_EMPTY.getMessage());
        }
        List<Long> scooterId = new ArrayList<>();
        scooterId.add(enter.getId());
        List<BaseScooterResult> scooterResultList = scooterService.scooterInfor(scooterId);

        ScooterMapResult scooterMapResult = orderDeliveryServiceMapper.driverInfo(enter);
        if (scooterMapResult != null) {
            if(scooterResultList.size()>0){
                scooterMapResult.setId(scooterResultList.get(0).getId());
                scooterMapResult.setLng(scooterResultList.get(0).getLongitule().toString());
                scooterMapResult.setLat(scooterResultList.get(0).getLatitude().toString());
                scooterMapResult.setBattery(scooterResultList.get(0).getBattery());
                scooterMapResult.setLicensePlate(scooterResultList.get(0).getLicensePlate());
                scooterMapResult.setStatus(scooterResultList.get(0).getAvailableStatus());
            }
        } else {
            scooterMapResult = ScooterMapResult.builder()
                    .id(scooterResultList.get(0).getId())
                    .lng(scooterResultList.get(0).getLongitule().toString())
                    .lat(scooterResultList.get(0).getLatitude().toString())
                    .battery(scooterResultList.get(0).getBattery())
                    .licensePlate(scooterResultList.get(0).getLicensePlate())
                    .status(scooterResultList.get(0).getAvailableStatus())
                    .build();
        }
        return scooterMapResult;
    }

    @Override
    public List<DeliveryNodeResult> nodeList(IdEnter enter) {
        return orderDeliveryServiceMapper.nodeList(enter);
    }

    /**
     * 订单重新分配
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult deliveryReset(DeliveryResetEnter enter) {

        CorDelivery corDelivery = deliveryService.getById(enter.getId());
        if (corDelivery == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(corDelivery.getStatus(), DeliveryStatusEnums.REJECTED.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getCode(), ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getMessage());
        }

        CorDriver corDriver = driverService.getById(enter.getDriverId());
        if (corDriver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if (corDelivery.getDelivererId().equals(corDriver.getUserId())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DELIVERY_CAN_NOT_ASSIGNED_THE_SAME_DRIVER.getCode(), ExceptionCodeEnums.DELIVERY_CAN_NOT_ASSIGNED_THE_SAME_DRIVER.getMessage());
        }
        if (!StringUtils.equals(corDriver.getStatus(), DriverStatusEnum.WORKING.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getCode(), ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getMessage());
        }

        TenantConfigInfoResult tenantConfigInfoResult = tenantBaseService.tenantConfigInfo(enter);

        corDelivery.setEta(DateUtil.parse(DateUtil.payDesignationTime(enter.getDuration()), DateUtil.DEFAULT_DATETIME_FORMAT));
        corDelivery.setStatus(DeliveryStatusEnums.PENDING.getValue());
        corDelivery.setDelivererId(corDriver.getUserId());
        corDelivery.setEta(DateUtils.addMinutes(new Date(), tenantConfigInfoResult.getEstimatedDuration().intValue()/60));
        corDelivery.setLabel(null);
        corDelivery.setUpdatedBy(enter.getUserId());
        corDelivery.setUpdatedTime(new Date());
        deliveryService.updateById(corDelivery);

        // 保存日志
        saveDeliveryNode(corDelivery, enter, null, DeliveryEventEnums.CHANAGE.getValue());

        jedisCluster.set(enter.getId().toString(), JSON.toJSONString(corDelivery));
        jedisCluster.expire(enter.getId().toString(), new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());

        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        String[] args = new String[]{corDelivery.getOrderNo(), new StringBuilder().append(corUserProfile.getFirstName() + " " + corUserProfile.getLastName()).toString()};

        PushMsgBo pushMsg = PushMsgBo.builder()
                .enter(enter)
                .pushType(PlatformTypeEnum.ANDROID.getValue())
                .bizId(corDelivery.getId())
                .bizType(BizType.DELIVERY.getValue())
                .status(DeliveryStatusEnums.PENDING.getValue())
                .args(args)
                .belongId(corDelivery.getDelivererId())
                .systemId(AppIDEnums.SAAS_APP.getSystemId())
                .appId(AppIDEnums.SAAS_APP.getAppId())
                .messagePriority(MessagePriorityEnums.COMMON_REMIND.getValue())
                .mesageType(MesageTypeEnum.SITE.getValue())
                .build();
        pushMsg(pushMsg);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 保存订单节点
     *
     * @param dto    配送单
     * @param enter  入参
     * @param reason 理由及其他字段
     */
    private void saveDeliveryNode(CorDelivery dto, GeneralEnter enter, String reason, String event) {
        CorDeliveryTrace deliveryTrace = new CorDeliveryTrace();
        deliveryTrace.setId(idAppService.getId(SequenceName.COR_DELIVERY_TRACE));
        deliveryTrace.setDr(0);
        deliveryTrace.setDeliveryId(dto.getId());
        deliveryTrace.setTenantId(dto.getTenantId());
        deliveryTrace.setUserId(dto.getDelivererId());
        deliveryTrace.setStatus(dto.getStatus());
        deliveryTrace.setReason(reason);
        deliveryTrace.setEvent(event);
        deliveryTrace.setEventTime(new Date());
        deliveryTrace.setLatitude(dto.getLatitude());
        deliveryTrace.setLongitude(dto.getLongitude());
        deliveryTrace.setGeohash(MapUtil.geoHash(dto.getLongitude().toString(), dto.getLatitude().toString()));
        deliveryTrace.setScooterId(dto.getScooterId());
        deliveryTrace.setCreatedBy(enter.getUserId());
        deliveryTrace.setCreatedTime(new Date());
        deliveryTrace.setUpdatedBy(enter.getUserId());
        deliveryTrace.setUpdatedTime(new Date());
        deliveryTraceService.save(deliveryTrace);
    }

    private String statusConversionEvent(String status) {

        if (status.equals(DeliveryStatusEnums.PENDING.getValue())) {
            return DeliveryEventEnums.CREATE.getValue();
        }
        if (status.equals(DeliveryStatusEnums.DELIVERING.getValue())) {
            return DeliveryEventEnums.START.getValue();
        }
        if (status.equals(DeliveryStatusEnums.TIMEOUT_COMPLETE.getValue())) {
            return DeliveryEventEnums.CANCEL.getValue();
        }
        if (status.equals(DeliveryStatusEnums.PENDING.getValue())) {
            return DeliveryEventEnums.TIMEOUT_COMPLETE.getValue();
        }
        if (status.equals(DeliveryStatusEnums.COMPLETED.getValue())) {
            return DeliveryEventEnums.COMPLETED.getValue();
        }
        if (status.equals(DeliveryStatusEnums.CANCEL.getValue())) {
            return DeliveryEventEnums.CANCEL.getValue();
        }
        return null;
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
