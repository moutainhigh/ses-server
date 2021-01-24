package com.redescooter.ses.web.delivery.service.express.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.redescooter.ses.api.common.vo.base.IdsEnter;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.delivery.vo.SelectDriverResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.expressDelivery.ExpressDeliveryDetailStatusEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderEventEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.task.TaskStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.foundation.vo.tenant.TenantConfigInfoResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.tool.utils.map.MapUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.ExpressOrderServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;
import com.redescooter.ses.web.delivery.dm.CorExpressDeliveryDetail;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.dm.CorExpressOrderTrace;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.ExcelService;
import com.redescooter.ses.web.delivery.service.base.CorDriverScooterService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryDetailService;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryService;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderService;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderTraceService;
import com.redescooter.ses.web.delivery.service.express.EdOrderService;
import com.redescooter.ses.web.delivery.service.express.EdOrderTraceService;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageEnter;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageResult;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderTraceResult;
import com.redescooter.ses.web.delivery.vo.QueryOrderDetailResult;
import com.redescooter.ses.web.delivery.vo.ScooterMapResult;
import com.redescooter.ses.web.delivery.vo.edorder.ChanageExpressOrderEnter;
import com.redescooter.ses.web.delivery.vo.edorder.DiverOrderInforResult;
import com.redescooter.ses.web.delivery.vo.edorder.ExpressOrderMapEnter;
import com.redescooter.ses.web.delivery.vo.edorder.ExpressOrderMapResult;
import com.redescooter.ses.web.delivery.vo.edorder.RefuseOrderDetailResult;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 4:34 下午
 * @ClassName: EdOrderServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class EdOrderServiceImpl implements EdOrderService {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private CorExpressOrderService expressOrderService;
    @Autowired
    private CorExpressOrderTraceService expressOrderTraceService;
    @Autowired
    private ExpressOrderServiceMapper expressOrderServiceMapper;

    @Autowired
    private CorExpressDeliveryService corExpressDeliveryService;
    @Autowired
    private EdOrderTraceService edOrderTraceService;
    @Autowired
    private CorExpressDeliveryDetailService corExpressDeliveryDetailService;
    @Autowired
    private CorDriverScooterService corDriverScooterService;
    @Reference
    private IdAppService idAppService;
    @Reference
    private GenerateService generateService;
    @Reference
    private TenantBaseService tenantBaseService;
    @Reference
    private ScooterService scooterService;


    @Override
    public void download(HttpServletResponse response) {

        String fileName = "expressOrder.xls";
        String path = "src/main/resources/template/";

        excelService.download(fileName, path, response);
    }

    /**
     * 表格订单导入
     *
     * @param enter
     * @return
     */
    @Override
    public ImportExcelOrderResult importOrders(ImportExcelOrderEnter enter) {
        ImportExcelOrderResult orderResult = excelService.readExcelDataByOrder(enter);
        return orderResult;
    }

    /**
     * 保存快递导入订单
     *
     * @param orderExcleDataList
     */
    @Override
    public void saveOrders(List<ExpressOrderExcleData> orderExcleDataList, GeneralEnter enter) {
        //批量插入
        List<CorExpressOrder> saveOrdersList = new ArrayList<>();
        List<CorExpressOrderTrace> saveExpressOrderTraceList = new ArrayList<>();

        //批次号生成
        String batchNo = generateService.getOrderNo();

        orderExcleDataList.forEach(order -> {
            Optional.ofNullable(order)
                    .ifPresent(o -> {
                        CorExpressOrder saverOrder = buildExpressOrderSingle((ImportExcelOrderEnter) enter, batchNo, order);
                        saveOrdersList.add(saverOrder);
                        // 保存日志
                        CorExpressOrderTrace saveExpressOrderTrace = createExpressOrderLogSingle(enter.getTenantId(), enter.getUserId(), saverOrder.getId(),
                                0, 0, 0,
                                saverOrder.getStatus(), ExpressOrderEventEnums.UNASGN.getValue(),
                                null, null, Boolean.FALSE);
                        saveExpressOrderTraceList.add(saveExpressOrderTrace);
                    });
        });

        if (saveOrdersList.size() > 0 && saveExpressOrderTraceList.size() > 0) {
            expressOrderService.batchInsert(saveOrdersList);
            expressOrderTraceService.batchInsert(saveExpressOrderTraceList);
        }

    }

    /**
     * 快递订单状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
        List<CountByStatusResult> list = expressOrderServiceMapper.countByStatus(enter);
        Map<String, Integer> map = new HashMap<>();
        if (list.size() > 0) {
            for (CountByStatusResult item : list) {
                map.put(item.getStatus(), item.getTotalCount());
            }
        }

        for (ExpressOrderStatusEnums status : ExpressOrderStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 快递订单列表分页查询
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<QueryExpressOrderByPageResult> list(QueryExpressOrderByPageEnter enter) {

        int totalRows = expressOrderServiceMapper.listCount(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, totalRows, expressOrderServiceMapper.list(enter));
    }

    /**
     * 订单详情查询
     *
     * @param enter
     * @return
     */
    @Override
    public QueryOrderDetailResult details(IdEnter enter) {

        QueryOrderDetailResult detail = expressOrderServiceMapper.detail(enter);

        Optional.ofNullable(detail).ifPresent(d -> {
            IdEnter tenantIdEnter = new IdEnter();
            BeanUtils.copyProperties(enter, tenantIdEnter);
            tenantIdEnter.setId(enter.getTenantId());
            QueryTenantResult tenantResult = tenantBaseService.queryTenantById(tenantIdEnter);
            List<QueryExpressOrderTraceResult> orderNode = expressOrderServiceMapper.getOrderNode(enter);

            d.setTenantAddress(tenantResult.getAddress());
            d.setTenantLat(tenantResult.getLatitude().toString());
            d.setTenantLng(tenantResult.getLongitude().toString());

            d.setSendMileage(MapUtil.getDistance(tenantResult.getLatitude() == null ? "0" : tenantResult.getLatitude().toString(),
                    tenantResult.getLongitude() == null ? "0" : tenantResult.getLongitude().toString(),
                    d.getSenderLatitude() == null ? "0" : d.getSenderLatitude(),
                    d.getSenderLongitude() == null ? "0" : d.getSenderLongitude()));
            d.setRecipientMileage(MapUtil.getDistance(tenantResult.getLatitude() == null ? "0" : tenantResult.getLatitude().toString(),
                    tenantResult.getLongitude() == null ? "0" : tenantResult.getLongitude().toString(),
                    d.getRecipientLatitude() == null ? "0" : d.getRecipientLatitude(),
                    d.getRecipientLongitude() == null ? "0" : d.getRecipientLongitude()));

            d.setExpressOrderTraceResultList(orderNode);
        });

        CorTenantScooter corTenantScooter = expressOrderServiceMapper.queryCorTenantScooterByDriverId(detail.getDriverId());
        if (corTenantScooter == null) {
            return detail;
        }
        detail.setLicensePlate(corTenantScooter.getLicensePlate());
        detail.setDriverLatitude(corTenantScooter.getLatitude().toString());
        detail.setDriverLongitule(corTenantScooter.getLongitule().toString());

        return detail;
    }

    /**
     * @param enter
     * @Description
     * @Author AlexLi
     * @Date 2020/2/3 13:46
     * @Param ExpressOrderMapEnter
     * @Return ExpressOrderMapResult
     * @desc 司机地图订单展示
     */
    @Override
    public ExpressOrderMapResult expressOrderMap(ExpressOrderMapEnter enter) {
        // 查询门店信息
        QueryTenantResult tenant = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        if (tenant == null) {
            return new ExpressOrderMapResult();
        }
        if (enter.getStatusList() == null || enter.getStatusList().size() == 0) {
            List<String> list = new ArrayList<>();
            list.add("0");
            enter.setStatusList(list);
        }
        // 司机车辆分配数据
        List<ScooterMapResult> scooterMapList = expressOrderServiceMapper.scooterMap(enter);

        List<CorExpressOrder> expressOrderList = expressOrderServiceMapper.mapOrderList(enter);

        List<QueryOrderDetailResult> orderResultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(expressOrderList)) {
            expressOrderList.forEach(item -> {
                QueryOrderDetailResult orderResult = new QueryOrderDetailResult();
                BeanUtils.copyProperties(item, orderResult);

                orderResult.setRecipientLongitude(item.getRecipientLongitude() == null ? null : item.getRecipientLongitude().toString());
                orderResult.setRecipientLatitude(item.getRecipientLatitude() == null ? null : item.getRecipientLatitude().toString());

                orderResultList.add(orderResult);
            });
        }
        return ExpressOrderMapResult.builder()
                .tenantId(tenant.getId())
                .tenantLat(tenant.getLatitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLatitude()))
                .tenantLng(tenant.getLongitude() == null ? String.valueOf(BigDecimal.ZERO) : String.valueOf(tenant.getLongitude()))
                .scooterMapResultList(scooterMapList)
                .orderList(orderResultList)
                .build();
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 15:28
     * @Param: enter
     * @Return: QueryOrderDetailResult
     * @method: diverOrderInfor
     */
    @Override
    public DiverOrderInforResult diverOrderlistById(IdEnter enter) {
        DiverOrderInforResult result = expressOrderServiceMapper.diverInfor(enter);

        if (result == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getCode(), ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getMessage());
        }
        // 车辆数据
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(result.getScooterId());
        List<BaseScooterResult> scooterListResult = scooterService.scooterInfor(scooterIdList);

        //订单数据
        List<QueryOrderDetailResult> orderList = expressOrderServiceMapper.driverOrderList(enter);

        if (scooterListResult.size() > 0) {
            result.setScooterId(scooterListResult.get(0).getId());
            result.setLicensePlate(scooterListResult.get(0).getLicensePlate());
            result.setBattery(scooterListResult.get(0).getBattery());
        }

        result.setOrderList(orderList);

        return result;
    }

    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/13 22:42
     * @Param: enter
     * @Return: RefuseOrderDetailResult
     * @desc: 拒绝订单详情
     */
    @Override
    public RefuseOrderDetailResult refuseOrderDetail(IdEnter enter) {
        CorExpressOrder corExpressOrder = expressOrderService.getById(enter.getId());
        if (corExpressOrder == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!SesStringUtils.equals(corExpressOrder.getStatus(), ExpressOrderStatusEnums.REJECTED.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getCode(), ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getMessage());
        }
        //获取
        return expressOrderServiceMapper.refuseOrderDetail(enter);
    }


    /**
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/13 23:47
     * @Param: enter
     * @Return: generalResult
     * @desc: 修改订单状态
     */
    @Override
    public GeneralResult chanageExpressOrder(ChanageExpressOrderEnter enter) {
        // 订单验证
        CorExpressOrder corExpressOrder = expressOrderService.getById(enter.getId());
        if (corExpressOrder == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!SesStringUtils.equals(corExpressOrder.getStatus(), ExpressOrderStatusEnums.REJECTED.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getCode(), ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getMessage());
        }

        // 查询司机当前正在使用的车辆
        QueryWrapper<CorDriverScooter> corDriverScooterQueryWrapper = new QueryWrapper<>();
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getDriverId());
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
        CorDriverScooter corDriverScooter = corDriverScooterService.getOne(corDriverScooterQueryWrapper);

        // 生成大订单
        CorExpressDelivery corExpressDelivery = saveCorExpressDelivery(enter, corDriverScooter.getScooterId());
        //生成 订单详情记录
        saveExpressDeliveyDetail(enter, corExpressDelivery);

        // 修改expressOrder 状态
        corExpressOrder.setStatus(ExpressOrderStatusEnums.ASGN.getValue());
        corExpressOrder.setUpdatedBy(enter.getUserId());
        corExpressOrder.setUpdatedTime(new Date());
        expressOrderService.updateById(corExpressOrder);
        // 订单记录
        saveOrderTraceSingle(enter, corExpressDelivery);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 取消订单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancelOrder(IdEnter enter) {

        CorExpressOrder expressOrder = expressOrderService.getById(enter.getId());

        if (expressOrder == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EXPRESS_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!expressOrder.getStatus().equals(ExpressOrderStatusEnums.REJECTED.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ORDER_WAS_NOT_REJECTED.getCode(), ExceptionCodeEnums.ORDER_WAS_NOT_REJECTED.getMessage());
        }
        expressOrder.setStatus(ExpressOrderStatusEnums.CANCEL.getValue());
        expressOrder.setUpdatedTime(new Date());

        expressOrderService.updateById(expressOrder);

        //加入记录
        createExpressOrderLogSingle(enter.getTenantId(), enter.getUserId(), expressOrder.getId(),
                0, 0, 0, ExpressOrderStatusEnums.CANCEL.getValue(),
                ExpressOrderEventEnums.UNASGN.getValue(), null, null, Boolean.TRUE);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 拒绝订单时 的司机列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<SelectDriverResult> refuseOrderDriverList(IdsEnter enter) {
        return expressOrderServiceMapper.refuseOrderDriverList(enter);
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/14 11:19
     * @Param: enter, corExpressDelivery
     * @Return: void
     * @desc: 订单详情记录
     */
    private void saveExpressDeliveyDetail(ChanageExpressOrderEnter enter, CorExpressDelivery corExpressDelivery) {
        CorExpressDeliveryDetail corExpressDeliveryDetail = new CorExpressDeliveryDetail();
        corExpressDeliveryDetail.setId(idAppService.getId(SequenceName.COR_EXPRESS_DELIVERY_DETAIL));
        corExpressDeliveryDetail.setDr(0);
        corExpressDeliveryDetail.setTenantId(enter.getTenantId());
        corExpressDeliveryDetail.setExpressDeliveryId(corExpressDelivery.getId());
        corExpressDeliveryDetail.setExpressOrderId(enter.getId());
        corExpressDeliveryDetail.setStatus(ExpressDeliveryDetailStatusEnums.ASGN.getValue());
        corExpressDeliveryDetail.setParcelQuantity(1);
        // todo 暂时以当前时间 加店铺超时时间
        corExpressDeliveryDetail.setAta(DateUtils.addMinutes(new Date(), 30));
        //enter.getIds().indexOf(item.getId())+1
        corExpressDeliveryDetail.setPrioritySort(0);
        corExpressDeliveryDetail.setCreatedBy(enter.getUserId());
        corExpressDeliveryDetail.setCreatedTime(new Date());
        corExpressDeliveryDetail.setUpdatedBy(enter.getUserId());
        corExpressDeliveryDetail.setUpdatedTime(new Date());
        corExpressDeliveryDetailService.save(corExpressDeliveryDetail);
    }

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/14 11:20
     * @Param: enter
     * @Return: CorExpressDelivery
     * @desc: saveCorExpressDelivery
     */
    private CorExpressDelivery saveCorExpressDelivery(ChanageExpressOrderEnter enter, Long scooterId) {
        CorExpressDelivery corExpressDelivery;
        corExpressDelivery = new CorExpressDelivery();
        corExpressDelivery.setId(idAppService.getId(SequenceName.COR_EXPRESS_DELIVERY));
        corExpressDelivery.setDr(0);
        corExpressDelivery.setScooterId(scooterId);
        corExpressDelivery.setTenantId(enter.getTenantId());
        corExpressDelivery.setStatus(TaskStatusEnums.PENDING.getValue());
        corExpressDelivery.setDriverId(enter.getDriverId());
        corExpressDelivery.setOrderSum(1);
        corExpressDelivery.setOrderCompleteNum(0);
        corExpressDelivery.setDeliveryDate(DateUtil.stringToDate(enter.getTaskTime()));
        corExpressDelivery.setCreateBy(enter.getUserId());
        corExpressDelivery.setCreateTime(new Date());
        corExpressDelivery.setUpdatedBy(enter.getUserId());
        corExpressDelivery.setUpdatedTime(new Date());
        corExpressDeliveryService.save(corExpressDelivery);
        return corExpressDelivery;
    }

    private void saveOrderTraceSingle(ChanageExpressOrderEnter enter, CorExpressDelivery corExpressDelivery) {
        // 门店信息
        TenantConfigInfoResult tenantConfigInfoResult = tenantBaseService.tenantConfigInfo(enter);
        // 保存日志
        BaseExpressOrderTraceEnter baseExpressOrderTraceEnter = BaseExpressOrderTraceEnter.builder()
                .id(idAppService.getId(SequenceName.COR_EXPRESS_ORDER_TRACE))
                .dr(0)
                .expressDeliveryId(corExpressDelivery.getId())
                .expressOrderId(enter.getId())
                .tenantId(enter.getTenantId())
                .driverId(enter.getDriverId())
                .status(ExpressOrderStatusEnums.ASGN.getValue())
                .event(ExpressOrderEventEnums.ASGN.getValue())
                .reason(null)
                .eventTime(new Date())
                .longitude(tenantConfigInfoResult.getLongitude())
                .latitude(tenantConfigInfoResult.getLatitude())
                .geohash(MapUtil.geoHash(tenantConfigInfoResult.getLongitude().toString(),
                        tenantConfigInfoResult.getLatitude().toString()))
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
        edOrderTraceService.saveExpressOrderTrace(baseExpressOrderTraceEnter);

    }

    /**
     * 保存订单导入记录
     *
     * @param tenantId
     * @param userId
     * @param expressOrderId
     * @param expressDeliveryId
     * @param driverId
     * @param scooterId
     * @param status
     * @param event
     * @param reason
     * @param otherMap
     * @param saveDB
     * @return
     */
    private CorExpressOrderTrace createExpressOrderLogSingle(long tenantId, long userId, long expressOrderId,
                                                             long expressDeliveryId, long driverId,
                                                             long scooterId, String status, String event,
                                                             String reason, Map<String, String> otherMap,
                                                             Boolean saveDB) {

        if (SesStringUtils.isBlank(String.valueOf(saveDB))) {
            saveDB = Boolean.FALSE;
        }

        CorExpressOrderTrace save = new CorExpressOrderTrace();
        save.setId(idAppService.getId(SequenceName.COR_EXPRESS_ORDER_TRACE));
        save.setDr(0);
        save.setExpressDeliveryId(expressDeliveryId);
        save.setTenantId(tenantId);
        save.setExpressOrderId(expressOrderId);
        save.setDriverId(driverId);
        save.setStatus(status);
        save.setEvent(event);
        save.setEventTime(new Date());
        save.setReason(reason);
        if (otherMap != null) {
            if (otherMap.containsKey(CorExpressOrderTrace.COL_LATITUDE) && otherMap.containsKey(CorExpressOrderTrace.COL_LONGITUDE)) {
                save.setLongitude(new BigDecimal(otherMap.getOrDefault(CorExpressOrderTrace.COL_LONGITUDE, "0")));
                save.setLatitude(new BigDecimal(otherMap.getOrDefault(CorExpressOrderTrace.COL_LONGITUDE, "0")));
                save.setGeohash("");
            }

            if (otherMap.containsKey(CorExpressOrderTrace.COL_SCOOTER_LONGITUDE) && otherMap.containsKey(CorExpressOrderTrace.COL_SCOOTER_LATITUDE)) {
                save.setScooterLongitude(new BigDecimal(otherMap.getOrDefault(CorExpressOrderTrace.COL_SCOOTER_LONGITUDE, "0")));
                save.setScooterLatitude(new BigDecimal(otherMap.getOrDefault(CorExpressOrderTrace.COL_SCOOTER_LATITUDE, "0")));
                save.setScooterGeohash("");
            }

        }
        save.setScooterId(scooterId);
        save.setCreatedBy(userId);
        save.setCreatedTime(new Date());
        save.setUpdatedBy(userId);
        save.setUpdatedTime(new Date());

        if (saveDB) {
            expressOrderTraceService.save(save);
        }
        return save;
    }


    private CorExpressOrder buildExpressOrderSingle(ImportExcelOrderEnter enter, String batchNo, ExpressOrderExcleData order) {
        CorExpressOrder saverOrder = new CorExpressOrder();
        BeanUtils.copyProperties(order, saverOrder);
        saverOrder.setId(idAppService.getId(SequenceName.COR_EXPRESS_ORDER));
        saverOrder.setDr(0);
        saverOrder.setTenantId(enter.getTenantId());
        saverOrder.setBatchNo(batchNo);
        saverOrder.setOrderNo(generateService.getOrderNo());
        saverOrder.setStatus(ExpressOrderStatusEnums.UNASGN.getValue());
        saverOrder.setAssignFlag(Boolean.FALSE);
        saverOrder.setParcelQuantity(1);
        saverOrder.setCreatedBy(enter.getUserId());
        saverOrder.setCreatedTime(new Date());
        saverOrder.setUpdatedBy(enter.getUserId());
        saverOrder.setUpdatedTime(new Date());
        if (order.getRecipientLatitude() == null || order.getRecipientLongitude() == null) {
            saverOrder.setRecipientLatitude(new BigDecimal("0"));
            saverOrder.setRecipientLongitude(new BigDecimal("0"));
            saverOrder.setRecipientGeohash("0");
        } else {
            saverOrder.setRecipientLatitude(order.getRecipientLatitude());
            saverOrder.setRecipientLongitude(order.getRecipientLongitude());
            //excel 如果经纬度 写反了 会出现geo算法越界
            try {
                saverOrder.setRecipientGeohash(MapUtil.geoHash(order.getRecipientLongitude().toString(), order.getRecipientLatitude().toString()));
            }catch (Exception e){
                throw new SesWebDeliveryException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
        }
        if (order.getSenderLatitude() == null || order.getSenderLongitude() == null) {
            saverOrder.setSenderLatitude(new BigDecimal("0"));
            saverOrder.setSenderLongitude(new BigDecimal("0"));
            saverOrder.setSenderGeohash("0");
        } else {
            saverOrder.setRecipientLatitude(order.getSenderLatitude());
            saverOrder.setRecipientLongitude(order.getSenderLongitude());
            saverOrder.setRecipientGeohash(MapUtil.geoHash(order.getSenderLongitude().toString(), order.getSenderLatitude().toString()));
        }
        SesStringUtils.objStringTrim(saverOrder);
        return saverOrder;
    }
}
