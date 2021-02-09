package com.redescooter.ses.service.mobile.b.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.delivery.BaseDeliveryEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.mobile.b.service.DeliveryTraceService;
import com.redescooter.ses.api.mobile.b.vo.DeliveryTraceDetailResult;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryTraceEnter;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.b.constant.SequenceName;
import com.redescooter.ses.service.mobile.b.dao.base.CorDeliveryTraceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDeliveryTrace;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.map.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:DeliveryTraceServiceImpl
 * @description: DeliveryTraceServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 16:15
 */
@DubboService
public class DeliveryTraceServiceImpl implements DeliveryTraceService {

    @Autowired
    private CorDeliveryTraceMapper corDeliveryTraceMapper;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ScooterService scooterService;

    /**
     * 保存订单节点
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult saveDeliveryTrace(List<SaveDeliveryTraceEnter<BaseDeliveryEnter>> enter) {
        if (CollectionUtils.isEmpty(enter)) {
            return null;
        }
        List<CorDeliveryTrace> saveList = new ArrayList<>();
        enter.forEach(item -> {
            CorDeliveryTrace deliveryTrace = buildCorDeliveryTraceSingle(item);
            saveList.add(deliveryTrace);
        });
        corDeliveryTraceMapper.batchInsert(saveList);
        return new GeneralResult(enter.get(0).getRequestId());
    }

    /**
     * 查询订单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeliveryTraceDetailResult> detail(IdEnter enter) {
        return null;
    }

    /**
     * buildCorDeliveryTraceSingle
     *
     * @param item
     * @return
     */
    private CorDeliveryTrace buildCorDeliveryTraceSingle(SaveDeliveryTraceEnter<BaseDeliveryEnter> item) {
        List<Long> scooterId = new ArrayList<>();
        scooterId.add(item.getT().getScooterId());
        List<BaseScooterResult> scooter = scooterService.scooterInfor(scooterId);

        CorDeliveryTrace deliveryTrace = new CorDeliveryTrace();
        deliveryTrace.setId(idAppService.getId(SequenceName.COR_DELIVERY_TRACE));
        deliveryTrace.setDr(0);
        deliveryTrace.setDeliveryId(item.getT().getId());
        deliveryTrace.setTenantId(item.getT().getTenantId());
        deliveryTrace.setUserId(item.getT().getDelivererId());
        deliveryTrace.setStatus(item.getT().getStatus());
        deliveryTrace.setEvent(item.getEvent());
        deliveryTrace.setReason(item.getReason());
        deliveryTrace.setEventTime(new Date());
        deliveryTrace.setLongitude(item.getT().getLongitude());
        deliveryTrace.setLatitude(item.getT().getLatitude());
        deliveryTrace.setGeohash(MapUtil.geoHash(item.getT().getLongitude().toString(), item.getT().getLatitude().toString()));
        deliveryTrace.setScooterId(item.getT().getScooterId());
        if (scooter.size() > 0) {
            deliveryTrace.setScooterLatitude(scooter.get(0).getLatitude());
            deliveryTrace.setScooterLongitude(scooter.get(0).getLongitule());
            deliveryTrace.setScooterLocationGeohash(MapUtil.geoHash(scooter.get(0).getLatitude().toString(), scooter.get(0).getLongitule().toString()));
        }
        deliveryTrace.setCreatedBy(item.getUserId());
        deliveryTrace.setCreatedTime(new Date());
        deliveryTrace.setUpdatedBy(item.getUserId());
        deliveryTrace.setUpdatedTime(new Date());
        return deliveryTrace;
    }
}
