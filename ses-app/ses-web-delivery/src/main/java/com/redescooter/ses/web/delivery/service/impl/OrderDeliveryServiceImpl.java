package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.OrderDeliveryServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.*;
import com.redescooter.ses.web.delivery.dm.*;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.OrderDeliveryService;
import com.redescooter.ses.web.delivery.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 3:42 下午
 * @ClassName: OrderDeliveryServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @Autowired
    private OrderDeliveryServiceMapper orderDeliveryServiceMapper;
    @Autowired
    private CorDeliveryMapper deliveryMapper;
    @Autowired
    private CorDriverScooterMapper driverScooterMapper;
    @Autowired
    private CorDeliveryTraceMapper deliveryTraceMapper;
    @Autowired
    private CorDriverMapper driverMapper;

    @Autowired
    private CorTenantScooterMapper corTenantScooterMapper;

    @Autowired
    private CorDeliveryTraceMapper corDeliveryTraceMapper;

    @Reference
    private TenantBaseService tenantBaseService;
    @Reference
    private IdAppService idAppService;
    @Reference
    private GenerateService generateService;
    @Reference
    private ScooterService scooterService;

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
        CorDriverScooter driverScooter = driverScooterMapper.selectOne(wrapper);
        if (driverScooter == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getCode(), ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getMessage());
        }
        if (enter.getId() == null || enter.getId() == 0) {

            CorDriver driver = driverMapper.selectById(enter.getDriverId());

            if (driver == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
            }
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
            BigDecimal drivenMileage = new BigDecimal(MapUtil.getDistance(enter.getLatitude(), enter.getLongitude(), tenant.getLatitude() == null ? "0" : String.valueOf(tenant.getLatitude()), tenant.getLongitude() == null ? "0" : String.valueOf(tenant.getLongitude())));
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
            deliveryMapper.insert(deliverySave);

            saveDeliveryNode(deliverySave, enter, null);

        } else {
            //编辑配送单
            CorDelivery oloDelivery = deliveryMapper.selectById(enter.getId());

            BeanUtils.copyProperties(enter, oloDelivery);

            CorDriver driver = driverMapper.selectById(enter.getDriverId());
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
            deliveryMapper.updateById(oloDelivery);
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

        List<Long> scooterId = new ArrayList<>();
        scooterId.add(details.getScooterId());
        List<BaseScooterResult> scooter = scooterService.scooterInfor(scooterId);

        QueryTenantResult queryTenantResult = tenantBaseService.queryTenantById(new IdEnter(details.getTenantId()));
        details.setTenantLat(queryTenantResult.getLatitude().toString());
        details.setTenantLng(queryTenantResult.getLongitude().toString());
        details.setBattery(String.valueOf(scooter.get(0).getBattery()));
        return details;
    }

    /**
     * @param enter
     * @return
     */
    @Override
    public List<SelectDriverResult> selectDriverList(GeneralEnter enter) {

        List<SelectDriverResult> selectDriverResults = orderDeliveryServiceMapper.selectDriverList(enter);

        return selectDriverResults;
    }

    /**
     * 关闭订单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult closed(ClosedEnter enter) {
        if (enter.getId() == null || enter.getId() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }
        CorDelivery delivery = deliveryMapper.selectById(enter.getId());

        if (!delivery.getStatus().equals(DeliveryStatusEnums.PENDING.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ORDER_HAS_STARTED_AND_CANNOT_BE_CANCELLED.getCode(), ExceptionCodeEnums.ORDER_HAS_STARTED_AND_CANNOT_BE_CANCELLED.getMessage());
        }

        delivery.setStatus(DeliveryStatusEnums.CANCEL.getValue());
        delivery.setUpdatedBy(enter.getUserId());
        delivery.setUpdatedTime(new Date());

        deliveryMapper.updateById(delivery);

        saveDeliveryNode(delivery, enter, enter.getReason());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 订单地址
     *
     * @param enter
     * @return
     */
    @Override
    public MapResult map(GeneralEnter enter) {
        // 查询门店信息
        QueryTenantResult tenant = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        // 查询车辆信息
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
        List<CorTenantScooter> corTenantScooterList = corTenantScooterMapper.selectList(corTenantScooterQueryWrapper);

        // 司机车辆分配数据
        List<ScooterMapResult> scooterMapList = orderDeliveryServiceMapper.scooterMap(enter);

        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.eq(CorDelivery.COL_DR, 0);
        corDeliveryQueryWrapper.eq(CorDelivery.COL_TENANT_ID, enter.getTenantId());
        List<CorDelivery> deliveryList = deliveryMapper.selectList(corDeliveryQueryWrapper);

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
                .tenantLng(tenant.getLongitude().toString())
                .tenantLat(tenant.getLatitude().toString())
                .scooterMapResultList(scooterMapList)
                .deliveryMapList(deliveryMapResultList)
                .build();
    }

    /**
     * 订单信息
     *
     * @param enter
     * @return
     */
    @Override
    public DriverOrderInfoResult driverDeliveryInfor(IdEnter enter) {

        DriverOrderInfoResult result = orderDeliveryServiceMapper.driverDeliveryInfor(enter);

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
        List<CorDelivery> deliveryList = deliveryMapper.selectList(corDeliveryQueryWrapper);

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

        return DriverOrderInfoResult.builder()
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
        List<Long> scooterId = new ArrayList<>();
        scooterId.add(enter.getId());
        List<BaseScooterResult> scooterResultList = scooterService.scooterInfor(scooterId);

        return ScooterMapResult.builder()
                .id(scooterResultList.get(0).getId())
                .lng(scooterResultList.get(0).getLongitule().toString())
                .lat(scooterResultList.get(0).getLatitude().toString())
                .battery(scooterResultList.get(0).getBattery())
                .licensePlate(scooterResultList.get(0).getLicensePlate())
                .status(scooterResultList.get(0).getAvailableStatus())
                .build();
    }

    @Override
    public List<DeliveryNodeResult> nodeList(IdEnter enter) {

        return orderDeliveryServiceMapper.nodeList(enter);
    }
    /**
     * 保存订单节点
     *
     * @param dto    配送单
     * @param enter  入参
     * @param reason 理由及其他字段
     */
    private void saveDeliveryNode(CorDelivery dto, GeneralEnter enter, String reason) {
        CorDeliveryTrace deliveryTrace = new CorDeliveryTrace();
        deliveryTrace.setId(idAppService.getId(SequenceName.COR_DELIVERY_TRACE));
        deliveryTrace.setDr(0);
        deliveryTrace.setDeliveryId(dto.getId());
        deliveryTrace.setTenantId(dto.getTenantId());
        deliveryTrace.setUserId(dto.getDelivererId());
        deliveryTrace.setStatus(dto.getStatus());
        deliveryTrace.setReason(reason);
        deliveryTrace.setEvent(statusConversionEvent(dto.getStatus()));
        deliveryTrace.setEventTime(new Date());
        deliveryTrace.setLatitude(dto.getLatitude());
        deliveryTrace.setLongitude(dto.getLongitude());
        deliveryTrace.setGeohash(MapUtil.geoHash(dto.getLongitude().toString(), dto.getLatitude().toString()));
        deliveryTrace.setScooterId(dto.getScooterId());
        deliveryTrace.setCreatedBy(enter.getUserId());
        deliveryTrace.setCreatedTime(new Date());
        deliveryTrace.setUpdatedBy(enter.getUserId());
        deliveryTrace.setUpdatedTime(new Date());
        deliveryTraceMapper.insert(deliveryTrace);
    }

    private String statusConversionEvent(String status) {

        if (status.equals(DeliveryStatusEnums.PENDING.getValue())) {
            return DeliveryEventEnums.CREATE.getValue();
        }
        if (status.equals(DeliveryStatusEnums.DELIVERING.getValue())) {
            return DeliveryEventEnums.START.getValue();
        }
        if (status.equals(DeliveryStatusEnums.CHANGED.getValue())) {
            return DeliveryEventEnums.REJECT.getValue();
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
        if (status.equals(DeliveryStatusEnums.TIMEOUT_WARNING.getValue())) {
            return DeliveryEventEnums.TIMEOUT.getValue();
        }
        return null;
    }
}
