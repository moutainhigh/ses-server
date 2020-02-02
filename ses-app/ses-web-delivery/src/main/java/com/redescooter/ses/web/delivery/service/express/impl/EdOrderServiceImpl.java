package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderEventEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.ExpressOrderServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.dm.CorExpressOrderTrace;
import com.redescooter.ses.web.delivery.service.ExcelService;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderService;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderTraceService;
import com.redescooter.ses.web.delivery.service.express.EdOrderService;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageEnter;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageResult;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderTraceResult;
import com.redescooter.ses.web.delivery.vo.QueryOrderDetailResult;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

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

    @Reference
    private IdAppService idAppService;
    @Reference
    private GenerateService generateService;
    @Reference
    private TenantBaseService tenantBaseService;

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

        List<QueryExpressOrderByPageResult> list = expressOrderServiceMapper.list(enter);

        return PageResult.create(enter, totalRows, list);
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

            d.setSendMileage(MapUtil.getDistance(tenantResult.getLatitude().toString(), tenantResult.getLongitude().toString(), d.getSenderLatitude().toString(),
                    d.getSenderLongitude().toString()));
            d.setRecipientMileage(MapUtil.getDistance(tenantResult.getLatitude().toString(), tenantResult.getLongitude().toString(), d.getRecipientLatitude().toString(),
                    d.getRecipientLongitude().toString()));

            d.setExpressOrderTraceResultList(orderNode);
        });

        return detail;
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

        if (StringUtils.isBlank(String.valueOf(saveDB))) {
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
        }
        if (order.getSenderLatitude() == null || order.getSenderLongitude() == null) {
            saverOrder.setSenderLatitude(new BigDecimal("0"));
            saverOrder.setSenderLongitude(new BigDecimal("0"));
            saverOrder.setSenderGeohash("0");
        }
        return saverOrder;
    }
}
