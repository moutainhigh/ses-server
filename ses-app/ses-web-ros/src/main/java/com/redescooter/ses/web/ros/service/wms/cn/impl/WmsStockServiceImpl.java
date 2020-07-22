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
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.bo.WmsStockDefaultDto;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    WmsStockDefaultDto defaultParam = new WmsStockDefaultDto();
    defaultParam.setAccessory(BomCommonTypeEnums.ACCESSORY.getValue());
    defaultParam.setParts(BomCommonTypeEnums.PARTS.getValue());
    defaultParam.setBattery(BomCommonTypeEnums.BATTERY.getValue());
    defaultParam.setScooter(BomCommonTypeEnums.SCOOTER.getValue());
    defaultParam.setCombination(BomCommonTypeEnums.COMBINATION.getValue());
    defaultParam.setFr(CurrencyUnitEnums.FR.getValue());
    OpeWhse purchas = opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE,WhseTypeEnums.PURCHAS.getValue()));
    OpeWhse assembly = opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE,WhseTypeEnums.ASSEMBLY.getValue()));
    defaultParam.setPurchas(purchas.getId());
    defaultParam.setAssembly(assembly.getId());
    int usableStockCount = wmsServiceMapper.usableStockCountByType(defaultParam);
    map.put(String.valueOf(PushTypeEnums.TAG.getValue()),usableStockCount);
    return map;
  }

  /**
   * 查询仓储显示可用库存集合
   *
   * @return
   */
  @Override
  public PageResult<WmsStockAvailableResult> getStockAvailableList(WmsStockEnter enter) {
    if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
      return PageResult.createZeroRowResult(enter);
    }
    WmsStockDefaultDto defaultParam = new WmsStockDefaultDto();
    defaultParam.setAccessory(BomCommonTypeEnums.ACCESSORY.getValue());
    defaultParam.setParts(BomCommonTypeEnums.PARTS.getValue());
    defaultParam.setBattery(BomCommonTypeEnums.BATTERY.getValue());
    defaultParam.setScooter(BomCommonTypeEnums.SCOOTER.getValue());
    defaultParam.setCombination(BomCommonTypeEnums.COMBINATION.getValue());
    defaultParam.setFr(CurrencyUnitEnums.FR.getValue());
    OpeWhse purchas = opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE,WhseTypeEnums.PURCHAS.getValue()));
    OpeWhse assembly = opewhseservice.getOne(new QueryWrapper<OpeWhse>().in(OpeWhse.COL_TYPE,WhseTypeEnums.ASSEMBLY.getValue()));
    defaultParam.setPurchas(purchas.getId());
    defaultParam.setAssembly(assembly.getId());
    int totalRows = wmsServiceMapper.wmsUsableStockCount(enter,defaultParam);
    if (totalRows == 0) {
      return PageResult.createZeroRowResult(enter);
    }

    return PageResult.create(enter, totalRows, wmsServiceMapper.wmsUsableStockList(enter,defaultParam));
  }

}
