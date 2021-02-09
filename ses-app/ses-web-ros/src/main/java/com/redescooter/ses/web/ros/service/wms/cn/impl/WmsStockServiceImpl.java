package com.redescooter.ses.web.ros.service.wms.cn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockClassTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.wms.cn.WmsServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.wms.cn.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameWmsStockServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/7/17 10:11
 * @Version V1.0
 **/
@Service
@Slf4j
public class WmsStockServiceImpl implements WmsStockService {

    @Autowired
    private WmsServiceMapper wmsServiceMapper;

    @Autowired
    private OpeOutwhOrderService opeOutwhOrderService;

    @Autowired
    private OpeWhseService opeWhseService;

    /**
     * 库存单状态统计
     *
     * @param enter
     * @retrn
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        List<String> list = new ArrayList<String>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            list.add(item.getValue());
        }
        List<OpeWhse> whseList = opeWhseService.list(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue(), WhseTypeEnums.ASSEMBLY.getValue(),
                WhseTypeEnums.ALLOCATE.getValue()));
        if (CollectionUtils.isEmpty(whseList)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        OpeWhse purchasWh = whseList.stream().filter(item -> StringUtils.equals(item.getType(), WhseTypeEnums.PURCHAS.getValue())).findFirst().orElse(null);
        OpeWhse assemblyWh = whseList.stream().filter(item -> StringUtils.equals(item.getType(), WhseTypeEnums.ASSEMBLY.getValue())).findFirst().orElse(null);
        OpeWhse allocateWh = whseList.stream().filter(item -> StringUtils.equals(item.getType(), WhseTypeEnums.ALLOCATE.getValue())).findFirst().orElse(null);

        //可用库存数据统计
        map.put(String.valueOf(WmsStockClassTypeEnums.AVAILABLE_ONE.getValue()), wmsServiceMapper.usableStockCountByType(purchasWh.getId(), assemblyWh.getId(), list, CurrencyUnitEnums.FR.getValue()));

        //待生产数据统计
        map.put(WmsStockClassTypeEnums.TOBEPREDICTED_TWO.getValue(), wmsServiceMapper.wmsBePredictedStockCountByType(purchasWh.getId(), list, CurrencyUnitEnums.FR.getValue()));

        //待入库数据统计
        map.put(WmsStockClassTypeEnums.TOBESTORED_THREE.getValue(), wmsServiceMapper.wmsStoredStockCountByType(enter, allocateWh.getId(), assemblyWh.getId(), list, CurrencyUnitEnums.FR.getValue()));

        //已出库数据统计
        map.put(WmsStockClassTypeEnums.OUTWH_FOUR.getValue(), wmsServiceMapper.wmsOutWhStockCountByType(WhOutStatusEnums.OUT_WH.getValue(), list, CurrencyUnitEnums.FR.getValue()));
        return map;
    }

    /**
     * 查询仓储显示可用库存集合
     *
     * @return
     */
    @Override
    public PageResult<WmsStockAvailableResult> list(WmsStockEnter enter) {
        if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
            return PageResult.createZeroRowResult(enter);
        }

        List<String> productType = new ArrayList<String>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            productType.add(item.getValue());
        }
        List<OpeWhse> whselist = opeWhseService.list(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue(), WhseTypeEnums.ASSEMBLY.getValue(), WhseTypeEnums.PURCHAS.getValue()));
        if (CollectionUtils.isEmpty(whselist)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        OpeWhse allocateWh = whselist.stream().filter(item -> StringUtils.equals(item.getType(), WhseTypeEnums.ALLOCATE.getValue())).findFirst().orElse(null);
        OpeWhse assemblyWh = whselist.stream().filter(item -> StringUtils.equals(item.getType(), WhseTypeEnums.ASSEMBLY.getValue())).findFirst().orElse(null);
        OpeWhse purchasWh = whselist.stream().filter(item -> StringUtils.equals(item.getType(), WhseTypeEnums.PURCHAS.getValue())).findFirst().orElse(null);

        if (allocateWh == null || assemblyWh == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        int totalRows = 0;
        List<WmsStockAvailableResult> stockResult = new ArrayList<WmsStockAvailableResult>();
        //可用库存数据列表
        if (StringUtils.equals(enter.getClassType(), String.valueOf(WmsStockClassTypeEnums.AVAILABLE_ONE.getValue()))) {
            totalRows = wmsServiceMapper.wmsUsableStockCount(enter, purchasWh.getId(), assemblyWh.getId(), productType, CurrencyUnitEnums.FR.getValue());
            stockResult = wmsServiceMapper.wmsUsableStockList(enter, purchasWh.getId(), assemblyWh.getId(), productType, CurrencyUnitEnums.FR.getValue());
        }
        //待生产库存数据列表
        if (StringUtils.equals(enter.getClassType(), String.valueOf(WmsStockClassTypeEnums.TOBEPREDICTED_TWO.getValue()))) {
            totalRows = wmsServiceMapper.wmsBePredictedStockCount(enter, purchasWh.getId(), productType, CurrencyUnitEnums.FR.getValue());
            stockResult = wmsServiceMapper.wmsBePredictedStockList(enter, purchasWh.getId(), productType, CurrencyUnitEnums.FR.getValue());
        }
        //待入库库存数据列表
        if (StringUtils.equals(enter.getClassType(), String.valueOf(WmsStockClassTypeEnums.TOBESTORED_THREE.getValue()))) {
            totalRows = wmsServiceMapper.wmsStoredStockCount(enter, allocateWh.getId(), assemblyWh.getId(), productType, CurrencyUnitEnums.FR.getValue());
            stockResult = wmsServiceMapper.wmsStoredStockList(enter, allocateWh.getId(), assemblyWh.getId(), productType, CurrencyUnitEnums.FR.getValue());
        }
        //已出库库存数据列表
        if (StringUtils.equals(enter.getClassType(), String.valueOf(WmsStockClassTypeEnums.OUTWH_FOUR.getValue()))) {
            totalRows = wmsServiceMapper.wmsOutWhStockCount(enter, CurrencyUnitEnums.FR.getValue());
            stockResult = wmsServiceMapper.wmsOutWhStockList(enter, CurrencyUnitEnums.FR.getValue());
        }

        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        stockResult.forEach(item ->
        {
            if (item.getPrice() != null && item.getIntTotal() != null) {
                item.setPrice(item.getIntTotal() * item.getPrice());
            }
        });
        return PageResult.create(enter, totalRows, stockResult);
    }


}
