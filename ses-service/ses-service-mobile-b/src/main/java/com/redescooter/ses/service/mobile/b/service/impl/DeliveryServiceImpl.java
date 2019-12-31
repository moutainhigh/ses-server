package com.redescooter.ses.service.mobile.b.service.impl;

import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryResultEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.delivery.BaseDeliveryEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.DeliveryService;
import com.redescooter.ses.api.mobile.b.service.DeliveryTraceService;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryDetailResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListResult;
import com.redescooter.ses.api.mobile.b.vo.RefuseEnter;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryTraceEnter;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.b.dao.DeliveryServiceMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDeliveryMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDelivery;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Reference
    private TenantBaseService tenantBaseService;

    @Reference
    private ScooterService scooterService;

    @Reference
    private ScooterIotService scooterIotService;

    /**
     * delviery 列表
     *
     * @param enter
     * @return
     */
    @Override

    public DeliveryListResult list(GeneralEnter enter) {
        // 查询订单状态及数量统计
        List<CountByStatusResult> list = deliveryServiceMapper.countByStatus(enter);

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

        result.setTenantLatitude(tenant.getLatitude());
        result.setTenantLongitude(tenant.getLongitude());
        result.setScooterLatitude(scooter.get(0).getLatitude());
        result.setScooterLongitude(scooter.get(0).getLongitule());
        result.setBattery(scooter.get(0).getBattery());
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
        delivery.setStatus(DeliveryStatusEnums.DELIVERING.getValue());
        delivery.setAtd(new Date());
        delivery.setEta(DateUtil.change(DateUtil.pay30()));
        delivery.setDrivenMileage(new BigDecimal(enter.getMileage()));
        delivery.setUpdatedBy(enter.getUserId());
        delivery.setUpdatedTime(new Date());
        corDeliveryMapper.updateById(delivery);
        // 记录日志
        saveDelivertTrace(enter, delivery, DeliveryEventEnums.START.getValue());
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
        deliveryStatus.add(DeliveryStatusEnums.TIME_OUT.getValue());
        deliveryStatus.add(DeliveryStatusEnums.TIMEOUT_WARNING.getValue());

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
        delivery.setDrivenDuration(DateUtil.timeComolete(delivery.getAta(), delivery.getAtd()).intValue() * 60);
        delivery.setCo2(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(delivery.getDrivenMileage().longValue())));
        delivery.setSavings(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(delivery.getDrivenMileage().longValue())));

        String deliveryResult = DateUtil.timeComolete(delivery.getEta(), delivery.getAta()).intValue() > 0 ? DeliveryResultEnums.DELAY.getValue() : DeliveryResultEnums.ONTIME.getValue();
        delivery.setResult(deliveryResult);
        delivery.setStatus(StringUtils.equals(DeliveryResultEnums.DELAY.getValue(), deliveryResult) ? DeliveryStatusEnums.TIMEOUT_COMPLETE.getValue() : DeliveryStatusEnums.COMPLETED.getValue());
        delivery.setUpdatedBy(enter.getUserId());
        delivery.setUpdatedTime(new Date());
        corDeliveryMapper.updateById(delivery);
        // 记录日志

        saveDelivertTrace(enter, delivery, DeliveryEventEnums.COMPLETED.getValue());
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
}
