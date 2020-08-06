package com.redescooter.ses.web.ros.service.wms.cn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PushTypeEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.wms.cn.WmsServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.wms.cn.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
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
  private OpeWhseService opewhseservice;

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
    List<OpeWhse> whselist = opewhseservice.list(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue(), WhseTypeEnums.ASSEMBLY.getValue()));
    //可用库存数据统计
    int usableStockCount = wmsServiceMapper.usableStockCountByType(whselist, list, CurrencyUnitEnums.FR.getValue());
    map.put(String.valueOf(WmsStockTypeEnums.AVAILABLE_ONE.getValue()), usableStockCount);

    //待生产数据统计
    int predictedStockCount= wmsServiceMapper.wmsBePredictedStockCountByType(opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue())).getId(), list, CurrencyUnitEnums.FR.getValue());
    map.put(String.valueOf(WmsStockTypeEnums.TOBEPREDICTED_TWO.getValue()), predictedStockCount);

    //待入库数据统计
    int storedStockCount= wmsServiceMapper.wmsStoredStockCountByType(opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.ASSEMBLY.getValue())).getId(), list, CurrencyUnitEnums.FR.getValue());
    map.put(String.valueOf(WmsStockTypeEnums.TOBESTORED_THREE.getValue()), storedStockCount);

    //已出库数据统计
    int outWhStockCount= wmsServiceMapper.wmsOutWhStockCountByType(WhOutStatusEnums.OUT_WH.getValue(), list, CurrencyUnitEnums.FR.getValue());
    map.put(String.valueOf(WmsStockTypeEnums.OUTWH_FOUR.getValue()), outWhStockCount);
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

        List<String> list = new ArrayList<String>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            list.add(item.getValue());
        }
        List<OpeWhse> whselist = opewhseservice.list(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue(), WhseTypeEnums.ASSEMBLY.getValue()));
        Long purchasId = opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue())).getId();
        Long assemblyId = opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE, WhseTypeEnums.ASSEMBLY.getValue())).getId();

    int totalRows = 0;
    List<WmsStockAvailableResult> stockResult = new ArrayList<WmsStockAvailableResult>();
    //可用库存数据列表
    if (StringUtils.equals(enter.getClassType(), String.valueOf(WmsStockTypeEnums.AVAILABLE_ONE.getValue()))){
        totalRows = wmsServiceMapper.wmsUsableStockCount(enter, whselist, list, CurrencyUnitEnums.FR.getValue());
        stockResult  = wmsServiceMapper.wmsUsableStockList(enter, whselist, list, CurrencyUnitEnums.FR.getValue());
    }
    //待生产库存数据列表
    if (StringUtils.equals(enter.getClassType(), String.valueOf(WmsStockTypeEnums.TOBEPREDICTED_TWO.getValue()))){
      totalRows = wmsServiceMapper.wmsBePredictedStockCount(enter,purchasId, list, CurrencyUnitEnums.FR.getValue());
      stockResult  = wmsServiceMapper.wmsBePredictedStockList(enter, purchasId, list, CurrencyUnitEnums.FR.getValue());
    }
    //待入库库存数据列表
    if (StringUtils.equals(enter.getClassType(),String.valueOf(WmsStockTypeEnums.TOBESTORED_THREE.getValue()))){
      totalRows = wmsServiceMapper.wmsStoredStockCount(enter,assemblyId, list, CurrencyUnitEnums.FR.getValue());
      stockResult  = wmsServiceMapper.wmsStoredStockList(enter,assemblyId, list, CurrencyUnitEnums.FR.getValue());
    }
    //已出库库存数据列表
    if (StringUtils.equals(enter.getClassType(),String.valueOf(WmsStockTypeEnums.OUTWH_FOUR.getValue()))){
      totalRows = wmsServiceMapper.wmsOutWhStockCount(enter, CurrencyUnitEnums.FR.getValue());
      stockResult  = wmsServiceMapper.wmsOutWhStockList(enter, CurrencyUnitEnums.FR.getValue());

    }

    if (totalRows == 0) {
      return PageResult.createZeroRowResult(enter);
    }
    stockResult.forEach(item ->
    {
      if (item.getPrice()!=null && item.getIntTotal()!=null){
        item.setPrice(item.getIntTotal()*item.getPrice());
      }
    });
    return PageResult.create(enter, totalRows, stockResult);
  }


}
