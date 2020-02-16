package com.redescooter.ses.service.mobile.b.service.express;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.expressDelivery.ExpressDeliveryDetailStatusEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderEventEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.enums.task.TaskStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.express.EdOrderDeliveryTraceService;
import com.redescooter.ses.api.mobile.b.service.express.EdOrderService;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteResult;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import com.redescooter.ses.api.mobile.b.vo.express.EdRfuseEnter;
import com.redescooter.ses.api.mobile.b.vo.express.OrderResult;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.service.mobile.b.constant.SequenceName;
import com.redescooter.ses.service.mobile.b.dao.EdOrderServiceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.*;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryDetailService;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryService;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderService;
import com.redescooter.ses.service.mobile.b.service.base.CorTenantScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.CO2MoneyConversionUtil;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class EdOrderServiceImpl implements EdOrderService {

    @Autowired
    private EdOrderServiceMapper edOrderServiceMapper;

    @Autowired
    private CorExpressOrderService corExpressOrderService;

    @Autowired
    private CorExpressDeliveryDetailService corExpressDeliveryDetailService;

    @Autowired
    private CorTenantScooterService corTenantScooterService;

    @Autowired
    private EdOrderDeliveryTraceService edOrderDeliveryTraceService;

    @Autowired
    private CorExpressDeliveryService corExpressDeliveryService;

    @Reference
    private IdAppService idAppService;

    @Reference
    private ScooterIotService scooterIotService;


    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:18
     * @Param: enter
     * @Return: PageResult<OrderResult>
     * @desc: 订单列表
     * @param enter
     */
    @Override
    public List<OrderResult> orderList(GeneralEnter enter) {
        List<OrderResult> orderList=edOrderServiceMapper.orderList(enter);
        if (CollectionUtils.isEmpty(orderList)){
            return null;
        }
        return orderList;
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:30
     * @Param: enter
     * @Return: OrderResult
     * @desc: 订单详情
     * @param enter
     */
    @Override
    public OrderResult orderDetail(IdEnter enter) {
        CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
        OrderResult orderResult=new OrderResult();
        BeanUtils.copyProperties(corExpressOrder,orderResult);
        return orderResult;
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:31
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 开始订单
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult start(StartEnter enter) {
        // 是否有正在进行的订单
        int count=edOrderServiceMapper.dirverShippingOrder(enter);
        if (count>0){
            throw new MobileBException(ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getCode(),ExceptionCodeEnums.DRIVER_HAS_AN_DELIVERY_IN_PROGRESS.getMessage());
        }
        //验证订单是否存在
        CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
        if (corExpressOrder==null){
            throw new MobileBException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        // 状态过滤
        if (!StringUtils.equals(corExpressOrder.getStatus(),ExpressOrderStatusEnums.ASGN.getValue())){
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 开启订单
        // 查询expressDeliveryDetail订单
        QueryWrapper<CorExpressDeliveryDetail> corExpressDeliveryDetailQueryWrapper=new QueryWrapper<>();
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_DR,0);
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_TENANT_ID,enter.getTenantId());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_STATUS, ExpressDeliveryDetailStatusEnums.ASGN.getValue());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_EXPRESS_ORDER_ID,enter.getId());
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
        CorExpressDelivery corExpressDelivery=corExpressDeliveryService.getById(deliveryDetail.getExpressDeliveryId());
        corExpressDelivery.setDrivenMileage(new BigDecimal(enter.getMileage()).add(corExpressDelivery.getDrivenMileage()));
        corExpressDelivery.setCo2(corExpressDelivery.getCo2().add(new BigDecimal(CO2MoneyConversionUtil.cO2Conversion(Long.valueOf(enter.getMileage())))));
        corExpressDelivery.setSavings(corExpressDelivery.getSavings().add(new BigDecimal(CO2MoneyConversionUtil.savingMoneyConversion(Long.valueOf(enter.getMileage())))));
        // 刚开始第一单
        if (corExpressDelivery.getOrderCompleteNum()==0){
            corExpressDelivery.setStatus(TaskStatusEnums.INPROGRESS.getValue());
            corExpressDelivery.setDeliveryStartTime(new Date());
        }
        corExpressDeliveryService.updateById(corExpressDelivery);

        // 获取正在骑行的车辆记录
       CorDriverScooter corDriverScooter=edOrderServiceMapper.queryScooterIdByUserId(enter.getUserId(),enter.getTenantId());
        // 开启导航
        IotScooterEnter iotScooterEnter =new IotScooterEnter();
        BeanUtils.copyProperties(enter,iotScooterEnter);
        iotScooterEnter.setId(corDriverScooter.getScooterId());
        iotScooterEnter.setEvent(CommonEvent.START.getValue());
        iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
        iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
        iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
        scooterIotService.navigation(iotScooterEnter);

        // 查询车辆信息
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper=new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR,0);
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID,corDriverScooter.getScooterId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID,enter.getTenantId());
        CorTenantScooter corTenantScooter=corTenantScooterService.getOne(corTenantScooterQueryWrapper);
        // 记录日志
        savrOrderTrace(enter,enter.getLng(),enter.getLat(),
                deliveryDetail.getExpressDeliveryId(),
                deliveryDetail.getExpressOrderId(),
                corDriverScooter.getDriverId(),
                corDriverScooter.getScooterId()
                , corTenantScooter.getLatitude()
                ,corTenantScooter.getLatitude()
                ,ExpressOrderStatusEnums.SHIPPING.getValue(),
                ExpressOrderEventEnums.SHIPPING.getValue());

        return new GeneralResult(enter.getRequestId());
    }



    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:32
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 拒绝订单
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult refuse(EdRfuseEnter enter) {
        //验证订单是否存在
        CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
        if (corExpressOrder==null){
            throw new MobileBException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        // 状态过滤
        if (!StringUtils.equals(corExpressOrder.getStatus(),ExpressOrderStatusEnums.SHIPPING.getValue())){
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 拒绝订单
        // 查询expressDeliveryDetail订单
        QueryWrapper<CorExpressDeliveryDetail> corExpressDeliveryDetailQueryWrapper=new QueryWrapper<>();
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_DR,0);
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_TENANT_ID,enter.getTenantId());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_EXPRESS_ORDER_ID,enter.getId());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_STATUS, ExpressDeliveryDetailStatusEnums.SHIPPING.getValue());
        CorExpressDeliveryDetail deliveryDetail = corExpressDeliveryDetailService.getOne(corExpressDeliveryDetailQueryWrapper);

        deliveryDetail.setStatus(ExpressDeliveryDetailStatusEnums.REJECTED.getValue());
        deliveryDetail.setUpdatedBy(enter.getUserId());
        deliveryDetail.setUpdatedTime(new Date());
        corExpressDeliveryDetailService.updateById(deliveryDetail);

        // 更新expressOrder 信息
        corExpressOrder.setStatus(ExpressOrderStatusEnums.REJECTED.getValue());
        corExpressOrder.setUpdatedBy(enter.getUserId());
        corExpressOrder.setUpdatedTime(new Date());
        corExpressOrderService.updateById(corExpressOrder);

        //查询task
        CorExpressDelivery corExpressDelivery = corExpressDeliveryService.getById(deliveryDetail.getExpressDeliveryId());
        corExpressDelivery.setOrderCompleteNum(corExpressDelivery.getOrderCompleteNum()+1);
        if ((corExpressDelivery.getOrderCompleteNum()).equals(corExpressDelivery.getOrderSum())){
            corExpressDelivery.setStatus(TaskStatusEnums.DELIVERED.getValue());
            corExpressDelivery.setDeliveryEndTime(new Date());
        }
        // 更新 task 信息
        corExpressDelivery.setUpdatedBy(enter.getUserId());
        corExpressDelivery.setUpdatedTime(new Date());
        corExpressDeliveryService.updateById(corExpressDelivery);


        // 获取正在骑行的车辆记录
        CorDriverScooter corDriverScooter=edOrderServiceMapper.queryScooterIdByUserId(enter.getUserId(),enter.getTenantId());

        // 查询车辆信息
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper=new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR,0);
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID,corDriverScooter.getScooterId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID,enter.getTenantId());
        CorTenantScooter corTenantScooter=corTenantScooterService.getOne(corTenantScooterQueryWrapper);

        // 结束导航
        IotScooterEnter iotScooterEnter =new IotScooterEnter();
        BeanUtils.copyProperties(enter,iotScooterEnter);
        iotScooterEnter.setId(corDriverScooter.getScooterId());
        iotScooterEnter.setEvent(CommonEvent.END.getValue());
        iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
        iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
        iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
        scooterIotService.navigation(iotScooterEnter);

        savrOrderTrace(enter,null,null,
                deliveryDetail.getExpressDeliveryId(),
                deliveryDetail.getExpressOrderId(),
                corDriverScooter.getDriverId(),
                corDriverScooter.getScooterId(),
                corTenantScooter.getLatitude(),
                corTenantScooter.getLatitude(),
                ExpressOrderStatusEnums.REJECTED.getValue(),
                ExpressOrderEventEnums.REJECTED.getValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 15:41
     * @Param: enter
     * @Return: CompleteResult
     * @desc: 完成订单
     * @param enter
     */
    @Transactional
    @Override
    public CompleteResult complete(CompleteEnter enter) {
        //验证订单是否存在
        CorExpressOrder corExpressOrder = corExpressOrderService.getById(enter.getId());
        if (corExpressOrder==null){
            throw new MobileBException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        // 状态过滤
        if (!StringUtils.equals(corExpressOrder.getStatus(),ExpressOrderStatusEnums.SHIPPING.getValue())){
            throw new MobileBException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        // 拒绝订单
        // 查询expressDeliveryDetail订单
        QueryWrapper<CorExpressDeliveryDetail> corExpressDeliveryDetailQueryWrapper=new QueryWrapper<>();
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_DR,0);
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_TENANT_ID,enter.getTenantId());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_STATUS, ExpressDeliveryDetailStatusEnums.SHIPPING.getValue());
        corExpressDeliveryDetailQueryWrapper.eq(CorExpressDeliveryDetail.COL_EXPRESS_ORDER_ID,enter.getId());
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
        corExpressOrderService.updateById(corExpressOrder);

        //维护delivery 中的 完成数量
        CorExpressDelivery corExpressDelivery=corExpressDeliveryService.getById(deliveryDetail.getExpressDeliveryId());
        int time = DateUtil.timeComolete(deliveryDetail.getAta(), deliveryDetail.getAtd()).intValue();
        corExpressDelivery.setDrivenDuration(time);
        corExpressDelivery.setOrderCompleteNum(corExpressDelivery.getOrderCompleteNum()+1);
        if (corExpressDelivery.getOrderCompleteNum().equals(corExpressDelivery.getOrderSum())){
            corExpressDelivery.setStatus(TaskStatusEnums.DELIVERED.getValue());
            corExpressDelivery.setDeliveryEndTime(new Date());
        }
        corExpressDeliveryService.updateById(corExpressDelivery);

        // 获取正在骑行的车辆记录
        CorDriverScooter corDriverScooter=edOrderServiceMapper.queryScooterIdByUserId(enter.getUserId(),enter.getTenantId());

        // 查询车辆信息
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper=new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR,0);
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID,corDriverScooter.getScooterId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID,enter.getTenantId());
        CorTenantScooter corTenantScooter=corTenantScooterService.getOne(corTenantScooterQueryWrapper);

        // 结束导航
        IotScooterEnter iotScooterEnter =new IotScooterEnter();
        BeanUtils.copyProperties(enter,iotScooterEnter);
        iotScooterEnter.setId(corDriverScooter.getScooterId());
        iotScooterEnter.setEvent(CommonEvent.END.getValue());
        iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
        iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
        iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
        scooterIotService.navigation(iotScooterEnter);

        // 订单日志
        savrOrderTrace(enter,enter.getLng(),
                enter.getLat(),
                deliveryDetail.getExpressDeliveryId(),
                deliveryDetail.getExpressOrderId(),
                corDriverScooter.getDriverId(),
                corDriverScooter.getScooterId(),
                corTenantScooter.getLatitude(),
                corTenantScooter.getLatitude(),
                ExpressOrderStatusEnums.COMPLETED.getValue(),
                ExpressOrderEventEnums.COMPLETED.getValue()
                );

        return CompleteResult.builder()
                .avg("1")
                .co2("2")
                .mileage("3")
                .money("4")
                .build();
    }


    private void savrOrderTrace(GeneralEnter enter,String lng,String lat,Long deliveryId,Long orderId,Long driverId,Long scooterId,BigDecimal scooterLng,BigDecimal scooterLat,String status,String event) {

        BaseExpressOrderTraceEnter baseExpressOrderTraceEnter=new BaseExpressOrderTraceEnter();
        BeanUtils.copyProperties(enter,baseExpressOrderTraceEnter);
        baseExpressOrderTraceEnter.setId(idAppService.getId(SequenceName.COR_EXPRESS_ORDER_TRACE));
        baseExpressOrderTraceEnter.setDr(0);
        baseExpressOrderTraceEnter.setExpressDeliveryId(deliveryId);
        baseExpressOrderTraceEnter.setExpressOrderId(orderId);
        baseExpressOrderTraceEnter.setTenantId(enter.getTenantId());
        baseExpressOrderTraceEnter.setDriverId(driverId);
        baseExpressOrderTraceEnter.setStatus(status);
        baseExpressOrderTraceEnter.setEvent(event);
        baseExpressOrderTraceEnter.setReason(null);
        baseExpressOrderTraceEnter.setEventTime(new Date());
        baseExpressOrderTraceEnter.setLongitude(new BigDecimal(StringUtils.isNotBlank(lng)==true?lng:"0"));
        baseExpressOrderTraceEnter.setLatitude(new BigDecimal(StringUtils.isNotBlank(lat)==true?lat:"0"));
        baseExpressOrderTraceEnter.setGeohash(MapUtil.geoHash(StringUtils.isNotBlank(lng)==true?lng:"0",StringUtils.isNotBlank(lat)==true?lat:"0"));
        baseExpressOrderTraceEnter.setScooterId(scooterId);
        baseExpressOrderTraceEnter.setScooterLatitude(scooterLat);
        baseExpressOrderTraceEnter.setScooterLongitude(scooterLng);
        baseExpressOrderTraceEnter.setScooterGeohash(MapUtil.geoHash(scooterLng.toString(),scooterLat.toString()));
        baseExpressOrderTraceEnter.setCreatedBy(enter.getUserId());
        baseExpressOrderTraceEnter.setCreatedTime(new Date());
        baseExpressOrderTraceEnter.setUpdatedBy(enter.getUserId());
        baseExpressOrderTraceEnter.setUpdatedTime(new Date());
        edOrderDeliveryTraceService.saveExpressOrderTrace(baseExpressOrderTraceEnter);
    }
}
