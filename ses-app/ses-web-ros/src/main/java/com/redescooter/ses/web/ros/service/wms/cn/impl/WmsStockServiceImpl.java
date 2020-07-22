package com.redescooter.ses.web.ros.service.wms.cn.impl;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.WmsServiceMapper;
import com.redescooter.ses.web.ros.service.wms.cn.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  /**
   * 查询仓储显示可用库存集合
   *
   * @return
   */
  @Override
  public PageResult<WmsStockAvailableResult> getStockAvailableList(WmsStockEnter page) {
    if (page.getKeyword() != null && page.getKeyword().length() > 50) {
      return PageResult.createZeroRowResult(page);
    }
    int totalRows = wmsServiceMapper.wmsUsableStockCount(page);
    if (totalRows == 0) {
      return PageResult.createZeroRowResult(page);
    }

    return PageResult.create(page, totalRows, wmsServiceMapper.wmsUsableStockList(page));
  }

  /**
   * 查询仓储待生产库存集合
   *
   * @param enter
   * @return
   */
  @Override
  public PageResult<WmsStockResult> getStockPredictedList(WmsStockEnter enter) {
    return null;
  }

  /**
   * 查询仓储待入库库存集合
   *
   * @param enter
   * @return
   */
  @Override
  public PageResult<WmsStockResult> getStockStoredList(WmsStockEnter enter) {
    return null;
  }

  /**
   * 查询仓储已出库库存集合
   *
   * @param enter
   * @return
   */
  @Override
  public PageResult<WmsStockResult> getStockOutWhList(WmsStockEnter enter) {
    return null;
  }

}
