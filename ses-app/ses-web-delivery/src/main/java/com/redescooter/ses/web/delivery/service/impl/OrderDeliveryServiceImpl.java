package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.OrderDeliveryServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDeliveryMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDeliveryTraceMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDriverMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDriverScooterMapper;
import com.redescooter.ses.web.delivery.dm.CorDelivery;
import com.redescooter.ses.web.delivery.dm.CorDeliveryTrace;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.OrderDeliveryService;
import com.redescooter.ses.web.delivery.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Reference
    private TenantBaseService tenantBaseService;
    @Reference
    private IdAppService idAppService;
    @Reference
    private GenerateService generateService;

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
        if (enter.getDriverId() == null || enter.getDriverId() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.DRIVER_CANNOT_EMPTY.getMessage());
        }
        if (enter.getParcelQuantity() == null || enter.getParcelQuantity() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.PACKAGES_CANNOT_BE_EMPTY.getCode(), ExceptionCodeEnums.PACKAGES_CANNOT_BE_EMPTY.getMessage());
        }
        if (StringUtils.isAnyBlank(enter.getRecipient(), enter.getRecipientEmail(), enter.getRecipientAddress(), enter.getRecipientTel(), enter.getCountryCode(), enter.getHouseInfo())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.RECIPIENT_INFORMATION_IS_MISSING.getCode(), ExceptionCodeEnums.RECIPIENT_INFORMATION_IS_MISSING.getMessage());
        }

        if (StringUtils.isAnyBlank(enter.getLatitude(), enter.getLongitude())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.MISSING_LATITUDE_AND_LONGITUDE.getCode(), ExceptionCodeEnums.MISSING_LATITUDE_AND_LONGITUDE.getMessage());
        }
        if (enter.getId() == null || enter.getId() == 0) {
            //创建配送单
            QueryTenantResult tenant = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

            QueryWrapper<CorDriverScooter> wrapper = new QueryWrapper<>();
            wrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getDriverId());
            wrapper.eq(CorDriverScooter.COL_DR, 0);
            wrapper.eq(CorDriverScooter.COL_STATUS, ScooterStatusEnums.USED.getValue());
            wrapper.isNotNull(CorDriverScooter.COL_END_TIME);
            CorDriverScooter driverScooter = driverScooterMapper.selectOne(wrapper);

            if (driverScooter == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getCode(), ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getMessage());
            }

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
            deliverySave.setDrivenMileage(new BigDecimal(MapUtil.getDistance(enter.getLatitude(), enter.getLongitude(), tenant.getLatitude().toString(), enter.getLongitude())));
            deliverySave.setScooterId(driverScooter.getScooterId());
            deliverySave.setTimeoutExpectde(enter.getTimeoutExpectde());
            //配送时长减去提前预警时长
            int timeDifference = Integer.parseInt(plannedTime) - Integer.parseInt(timeoutExpectde);
            //设置超时预警时间
            deliverySave.setTimeOut(DateUtils.addMinutes(new Date(), timeDifference));
            deliverySave.setPlannedTime(plannedTime);
            deliverySave.setCreatedBy(enter.getUserId());
            deliverySave.setCreatedTime(new Date());
            deliverySave.setUpdatedBy(enter.getUserId());
            deliverySave.setUpdatedTime(new Date());
            deliveryMapper.insert(deliverySave);

            saveDeliveryNode(deliverySave, enter);

        } else {
            //编辑配送单
            CorDelivery oloDelivery = deliveryMapper.selectById(enter.getId());
            BeanUtils.copyProperties(enter, oloDelivery);

            QueryWrapper<CorDriverScooter> wrapper = new QueryWrapper<>();
            wrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getDriverId());
            wrapper.eq(CorDriverScooter.COL_DR, 0);
            wrapper.eq(CorDriverScooter.COL_STATUS, ScooterStatusEnums.USED.getValue());
            wrapper.isNotNull(CorDriverScooter.COL_END_TIME);
            CorDriverScooter driverScooter = driverScooterMapper.selectOne(wrapper);
            if (driverScooter == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getCode(), ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getMessage());
            }
            CorDriver driver = driverMapper.selectById(enter.getDriverId());

            if (driver == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
            }

            oloDelivery.setScooterId(driverScooter.getScooterId());
            oloDelivery.setLatitude(new BigDecimal(enter.getLatitude()));
            oloDelivery.setLongitude(new BigDecimal(enter.getLongitude()));
            oloDelivery.setGeohash(MapUtil.geoHash(enter.getLongitude(), enter.getLatitude()));
            oloDelivery.setTimeoutExpectde(enter.getTimeoutExpectde());
            //配送时长减去提前预警时长
            int timeDifference = Integer.parseInt(plannedTime) - Integer.parseInt(timeoutExpectde);
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

    private void saveDeliveryNode(CorDelivery dto, SaveOrderDeliveryEnter enter) {
        CorDeliveryTrace deliveryTrace = new CorDeliveryTrace();
        deliveryTrace.setId(idAppService.getId(SequenceName.COR_DELIVERY_TRACE));
        deliveryTrace.setDr(0);
        deliveryTrace.setDeliveryId(dto.getId());
        deliveryTrace.setTenantId(dto.getTenantId());
        deliveryTrace.setUserId(dto.getDelivererId());
        deliveryTrace.setStatus(dto.getStatus());
        deliveryTrace.setEvent(DeliveryEventEnums.CREATE.getValue());
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
}
