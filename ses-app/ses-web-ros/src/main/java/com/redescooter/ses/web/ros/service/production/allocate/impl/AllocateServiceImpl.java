package com.redescooter.ses.web.ros.service.production.allocate.impl;

import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.PurchasingTypeEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderEventEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.production.allocate.AllocateService;
import com.redescooter.ses.web.ros.vo.production.ProductPartsListEnter;
import com.redescooter.ses.web.ros.vo.production.ProductPartsResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderNodeResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderPartResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderResult;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAllocateEnter;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @ClassName:AllocateServiceImpl
 * @description: AllocateServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:10
 */
@Service
public class AllocateServiceImpl implements AllocateService {

    /**
     * 类型统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {
        Map<String, Integer> stringMap = Maps.newHashMap();
        for (PurchasingTypeEnums item : PurchasingTypeEnums.values()) {
            stringMap.put(item.getValue(), 0);
        }
        return stringMap;
    }

    /**
     * 调拨单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AllocateOrderResult> list(AllocateOrderEnter enter) {
        List<AllocateOrderResult> list = Lists.newArrayList();
        list.add(AllocateOrderResult.builder()
                .id(10000L)
                .allocateN("423423432423")
                .status(AllocateOrderStatusEnums.INPROGRESS.getValue())
                .qty(100)
                .consigneeId(100000L)
                .consigneeFirstName("alex")
                .consigneeLastName("alex")
                .consigneePhone("324234234")
                .consigneeEmail("alex@13.com")
                .createdTime(new Date())
                .build());
        return PageResult.create(enter, 1, list);
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public AllocateOrderResult detail(IdEnter enter) {
        return AllocateOrderResult.builder()
                .id(10000L)
                .allocateN("423423432423")
                .status(AllocateOrderStatusEnums.INPROGRESS.getValue())
                .qty(100)
                .consigneeId(100000L)
                .consigneeFirstName("alex")
                .consigneeLastName("alex")
                .consigneePhone("324234234")
                .consigneeEmail("alex@13.com")
                .createdTime(new Date())
                .build();
    }

    /**
     * 调拨单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<AllocateOrderNodeResult> allocateOrderNode(IdEnter enter) {
        List<AllocateOrderNodeResult> list = Lists.newArrayList();
        list.add(AllocateOrderNodeResult.builder()
                .id(1000000L)
                .status(AllocateOrderStatusEnums.INPROGRESS.getValue())
                .event(AllocateOrderEventEnums.INPROGRESS.getValue())
                .createdBy(100000L)
                .createdByFirstName("liuke")
                .createdByLastName("eqweqw")
                .createdTime(new Date())
                .build());
        return list;
    }

    /**
     * 调拨单部件列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<AllocateOrderPartResult> allocateOrderDetailPartsList(IdEnter enter) {
        List<AllocateOrderPartResult> list = Lists.newArrayList();
        list.add(AllocateOrderPartResult.builder()
                .id(100000L)
                .partsN("4234234234")
                .type(BomCommonTypeEnums.BATTERY.getValue())
                .enName("dadasdasda")
                .cnName("eerrrwerqwe")
                .qty(10000)
                .build());
        return list;
    }

    /**
     * 开始调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult startAllocate(IdEnter enter) {
        return null;
    }

    /**
     * 取消调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancelAllocate(IdEnter enter) {
        return null;
    }

    /**
     * 部件列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductPartsResult> allocatePartsList(ProductPartsListEnter enter) {
        List<ProductPartsResult> list = Lists.newArrayList();
        list.add(ProductPartsResult.builder()
                .id(100000L)
                .partsN("4234234234")
                .type(BomCommonTypeEnums.BATTERY.getValue())
                .enName("dadasdasda")
                .cnName("eerrrwerqwe")
                .supplierId(10000L)
                .supplierName("Flex")
                .leadTime(20)
                .stocks(2000)
                .build());
        return list;
    }

    /**
     * 入库调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult inWhAllocate(IdEnter enter) {
        return null;
    }

    /**
     * 保存调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveAllocate(SaveAllocateEnter enter) {
        return null;
    }
}
