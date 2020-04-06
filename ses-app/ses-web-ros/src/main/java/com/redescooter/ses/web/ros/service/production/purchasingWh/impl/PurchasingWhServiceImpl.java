package com.redescooter.ses.web.ros.service.production.purchasingWh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.wh.PurchasingWhTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.production.PurchasingWhServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.purchasingWh.PurchasingWhService;
import com.redescooter.ses.web.ros.vo.production.wh.AssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListResult;
import com.redescooter.ses.web.ros.vo.production.wh.OutWhResult;
import com.redescooter.ses.web.ros.vo.production.wh.QcingListResult;
import com.redescooter.ses.web.ros.vo.production.wh.TobeStoredResult;
import com.redescooter.ses.web.ros.vo.production.wh.WasteResult;
import com.redescooter.ses.web.ros.vo.production.wh.WhEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName:PurchasingWhServiceImpl
 * @description: PurchasingWhServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 14:16
 */
@Service
public class PurchasingWhServiceImpl implements PurchasingWhService {

    @Autowired
    private PurchasingWhServiceMapper purchasingWhServiceMapper;

    @Autowired
    private OpeWhseService opeWhseService;

    /**
     * 类型统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {
        Map<String, Integer> map = Maps.newHashMap();
        for (PurchasingWhTypeEnums item : PurchasingWhTypeEnums.values()) {
            map.put(item.getValue(), 0);
        }
        return map;
    }

    /**
     * 类型 列表
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> productTypeList(GeneralEnter enter) {
        Map<String, String> map = Maps.newHashMap();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            map.put(item.getValue(), item.getCode());
        }
        return map;
    }

    /**
     * 可用列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AvailableListResult> availableList(WhEnter enter) {
        OpeWhse opeWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.PURCHAS.getValue())).get(0);

        if (StringUtils.isBlank(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }

        int count = purchasingWhServiceMapper.availableListCount(enter, opeWhse.getId());
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AvailableListResult> availableList = purchasingWhServiceMapper.availableList(enter, opeWhse.getId());

        // 查询质检批次号
        List<Long> partIds = Lists.newArrayList();

        availableList.stream().forEach(item -> {
            partIds.add(item.getId());
        });
        List<OpePurchasBQc> opePurchasBQcList = purchasingWhServiceMapper.PurchasBQc(partIds, PurchasingStatusEnums.IN_PURCHASING_WH.getValue());


        Map<Long, Set<String>> batchNMap = Maps.newHashMap();
        availableList.forEach(item -> {
            Set<String> batchNList = Sets.newHashSet();
            opePurchasBQcList.forEach(qc -> {
                if (item.getId().equals(qc.getPartsId())) {
                    batchNList.add(qc.getBatchNo());
                }
            });
            batchNMap.put(item.getId(), batchNList);
        });

        //批次号 设置
        availableList.forEach(item -> {
            if (batchNMap.containsKey(item.getId())) {
                if (StringUtils.isNotEmpty(enter.getKeyword())) {
                    batchNMap.forEach((key, value) -> {
                        if (value.contains(enter.getKeyword())) {
                            item.setBatchNList(new ArrayList<>(batchNMap.get(item.getId())));
                        }
                    });
                } else {
                    item.setBatchNList(new ArrayList<>(batchNMap.get(item.getId())));
                }
            }
        });

        availableList.removeIf(item -> CollectionUtils.isEmpty(item.getBatchNList()));
        return PageResult.create(enter, count, availableList);
    }

    /**
     * 质检中列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<QcingListResult> qcingList(WhEnter enter) {
        if (StringUtils.isBlank(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        int count = purchasingWhServiceMapper.qcingListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.qcingList(enter));
    }

    /**
     * 待入库列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<TobeStoredResult> tobeStoredList(WhEnter enter) {
        if (StringUtils.isBlank(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        int count = purchasingWhServiceMapper.tobeStoredListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.tobeStoredList(enter));
    }

    /**
     * 出库列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<OutWhResult> outWhList(WhEnter enter) {
        if (StringUtils.isBlank(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        List<OpeWhse> opeWhseList = checkWhse(Lists.newArrayList(WhseTypeEnums.ASSEMBLY.getValue(), WhseTypeEnums.ALLOCATE.getValue()));

        List<Long> whseIds = new ArrayList<>();
        opeWhseList.forEach(item -> {
            whseIds.add(item.getId());
        });
        int count = purchasingWhServiceMapper.outWhListCount(enter, whseIds);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.outWhList(enter, whseIds));
    }

    /**
     * 废料列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WasteResult> wasteList(WhEnter enter) {
        int count = purchasingWhServiceMapper.wasteListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.wasteList(enter));
    }

    /**
     * 可组装的产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<AssemblyProductResult> canAssemblyProductList(GeneralEnter enter) {
        return null;
    }

    private List<OpeWhse> checkWhse(List<String> types) {
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.in(OpeWhse.COL_TYPE, types);
        List<OpeWhse> opeWhseList = opeWhseService.list(opeWhseQueryWrapper);
        if (opeWhseList.size() != types.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        return opeWhseList;
    }
}
