package com.redescooter.ses.service.hub.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryLableEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.hub.job.RunRtDeliveryTaskExecutorServiceJob;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDelivery;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDeliveryTrace;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorDeliveryService;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorDeliveryTraceService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.map.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:RunRtDeliveryTaskExecutorServiceJobImpl
 * @description: RunRtDeliveryTaskExecutorServiceJobImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/16 14:58
 */
@Service
public class RunRtDeliveryTaskExecutorServiceJobImpl implements RunRtDeliveryTaskExecutorServiceJob {

    @Autowired
    private CorDeliveryService corDeliveryService;

    @Autowired
    private CorDeliveryTraceService corDeliveryTraceService;

    @Reference
    private IdAppService idAppService;

    /**
     * 订单超时
     *
     * @param enter
     * @return
     */
    @Override
    public JobResult deliveryTimeOut(GeneralEnter enter) {
        //todo 年后aron 来了之后 去掉 清楚掉终结状态下 标签
        QueryWrapper<CorDelivery> updaCorDeliveryQueryWrapper = new QueryWrapper<>();
        updaCorDeliveryQueryWrapper.in(CorDelivery.COL_STATUS, DeliveryStatusEnums.TIMEOUT_COMPLETE.getValue(), DeliveryStatusEnums.REJECTED.getValue(), DeliveryStatusEnums.COMPLETED.getValue(),
                DeliveryStatusEnums.CANCEL.getValue());
        updaCorDeliveryQueryWrapper.eq(CorDelivery.COL_DR, 0);
        updaCorDeliveryQueryWrapper.eq(CorDelivery.COL_LABEL, DeliveryLableEnums.TIMEOUT_WARNING.getValue());
        List<CorDelivery> updateCorDeliveryList = corDeliveryService.list(updaCorDeliveryQueryWrapper);
        updateCorDeliveryList.forEach(item -> {
            item.setLabel(null);
        });


        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.le(CorDelivery.COL_ETA, new Date());
        corDeliveryQueryWrapper.isNull(CorDelivery.COL_LABEL);
        corDeliveryQueryWrapper.in(CorDelivery.COL_STATUS, DeliveryStatusEnums.DELIVERING.getValue(), DeliveryStatusEnums.PENDING.getValue());

        List<CorDelivery> corDeliveryList = corDeliveryService.list(corDeliveryQueryWrapper);

        List<CorDelivery> updateCorDelivery = new ArrayList<>();
        List<CorDeliveryTrace> saveDeliveryTraceList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(corDeliveryList)) {
            for (CorDelivery item : corDeliveryList) {
                item.setLabel(DeliveryLableEnums.TIMEOUT_WARNING.getValue());
                updateCorDelivery.add(item);
                CorDeliveryTrace trace = buildCorDeliveryTrace(item);
                saveDeliveryTraceList.add(trace);
            }
        }
        if (CollectionUtils.isNotEmpty(saveDeliveryTraceList)) {
            corDeliveryTraceService.batchInsert(saveDeliveryTraceList);
        }
        // 将 清楚掉标签数据 放到list中做更新
        updateCorDelivery.addAll(updateCorDeliveryList);
        if (CollectionUtils.isNotEmpty(updateCorDelivery)) {
            corDeliveryService.updateBatch(updateCorDelivery);
        }
        return JobResult.success();
    }

    private CorDeliveryTrace buildCorDeliveryTrace(CorDelivery item) {
        CorDeliveryTrace trace = new CorDeliveryTrace();
        trace.setId(idAppService.getId(SequenceName.COR_DELIVERY_TRACE));
        trace.setDr(0);
        trace.setDeliveryId(item.getId());
        trace.setTenantId(item.getTenantId());
        trace.setUserId(item.getDelivererId());
        trace.setStatus(item.getStatus());
        trace.setEvent(DeliveryEventEnums.TIMEOUT.getValue());
        trace.setReason(null);
        trace.setEventTime(item.getEta());
        trace.setLongitude(item.getLongitude());
        trace.setLatitude(item.getLatitude());
        trace.setGeohash(MapUtil.geoHash(item.getLongitude().toString(), item.getLatitude().toString()));
        trace.setScooterId(item.getScooterId());
        trace.setCreatedBy(item.getCreatedBy());
        trace.setCreatedTime(item.getEta());
        trace.setUpdatedBy(item.getUpdatedBy());
        trace.setUpdatedTime(item.getEta());
        return trace;
    }
}
