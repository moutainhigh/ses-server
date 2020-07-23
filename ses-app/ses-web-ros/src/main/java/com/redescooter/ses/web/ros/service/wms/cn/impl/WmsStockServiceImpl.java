package com.redescooter.ses.web.ros.service.wms.cn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PushTypeEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.wms.cn.WmsServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.wms.cn.WmsStockService;
import com.redescooter.ses.web.ros.vo.bo.WmsStockDefaultDto;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import lombok.extern.slf4j.Slf4j;
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
    int usableStockCount = wmsServiceMapper.usableStockCountByType(whselist, list, CurrencyUnitEnums.FR.getValue());
    map.put(String.valueOf(PushTypeEnums.TAG.getValue()), usableStockCount);
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
    int totalRows = wmsServiceMapper.wmsUsableStockCount(enter, whselist, list, CurrencyUnitEnums.FR.getValue());
    if (totalRows == 0) {
      return PageResult.createZeroRowResult(enter);
    }

    return PageResult.create(enter, totalRows, wmsServiceMapper.wmsUsableStockList(enter, whselist, list, CurrencyUnitEnums.FR.getValue()));
  }

}
