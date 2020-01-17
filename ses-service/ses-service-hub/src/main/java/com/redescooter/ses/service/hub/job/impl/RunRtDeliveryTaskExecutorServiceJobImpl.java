package com.redescooter.ses.service.hub.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
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
import com.redescooter.ses.tool.utils.MapUtil;
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
        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.le(CorDelivery.COL_ETA, new Date());
        corDeliveryQueryWrapper.eq(CorDelivery.COL_DR, 0);
        corDeliveryQueryWrapper.in(CorDelivery.COL_STATUS, DeliveryStatusEnums.DELIVERING.getValue(), DeliveryStatusEnums.REJECTED.getValue(), DeliveryStatusEnums.PENDING.getValue());

        List<CorDelivery> corDeliveryList = corDeliveryService.list(corDeliveryQueryWrapper);

        //查询所有已拒绝的订单
        QueryWrapper<CorDeliveryTrace> corDeliveryTraceQueryWrapper = new QueryWrapper<>();
        corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_EVENT, DeliveryEventEnums.TIMEOUT.getValue());
        corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_DR, 0);
        List<CorDeliveryTrace> corDeliveryTraceList = corDeliveryTraceService.list(corDeliveryTraceQueryWrapper);

        List<Long> timeOutDeliveryIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(corDeliveryTraceList)) {
            corDeliveryTraceList.forEach(item -> {
                timeOutDeliveryIds.add(item.getDeliveryId());
            });
        }

        List<CorDeliveryTrace> saveDeliveryTraceList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(corDeliveryList)) {
            for (CorDelivery item : corDeliveryList) {
                // 若 该订单的超时事件已经 保存过了 就剔除掉
                if (CollectionUtils.isNotEmpty(timeOutDeliveryIds) && timeOutDeliveryIds.contains(item.getId())) {
                    continue;
                }
                CorDeliveryTrace trace = buildCorDeliveryTrace(item);
                saveDeliveryTraceList.add(trace);
            }
        }
        if (CollectionUtils.isNotEmpty(saveDeliveryTraceList)) {
            corDeliveryTraceService.batchInsert(saveDeliveryTraceList);
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
        trace.setCreatedTime(new Date());
        trace.setUpdatedBy(item.getUpdatedBy());
        trace.setUpdatedTime(new Date());
        return trace;
    }
}
