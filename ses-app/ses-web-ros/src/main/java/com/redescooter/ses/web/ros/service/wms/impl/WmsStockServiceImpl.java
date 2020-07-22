package com.redescooter.ses.web.ros.service.wms.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.WmsServiceMapper;
import com.redescooter.ses.web.ros.service.wms.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.WmsStockEnter;
import com.redescooter.ses.web.ros.vo.wms.WmsStockResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  /**
   * 库存单状态统计
   *
   * @param enter
   * @retrn
   */
  @Override
  public Map<String, Integer> countByType(GeneralEnter enter) {
    Map<String, Integer> map = new HashMap<>();
    int usableStockCount = wmsServiceMapper.usableStockCountByType(enter);
    map.put("1",usableStockCount);
    return map;
  }

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

}
