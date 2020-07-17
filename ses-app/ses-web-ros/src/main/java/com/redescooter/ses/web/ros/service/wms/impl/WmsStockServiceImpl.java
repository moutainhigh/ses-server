package com.redescooter.ses.web.ros.service.wms.impl;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.WmsServiceMapper;
import com.redescooter.ses.web.ros.service.wms.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.WmsStockEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
   * @return
   */
  @Override
  public PageResult<WmsStockAvailableResult> getStockAvailableList(WmsStockEnter page) {
    if (page.getKeyword()!=null && page.getKeyword().length()>50){
      return PageResult.createZeroRowResult(page);
    }
    int totalRows = wmsServiceMapper.WmsUsableStockCount(page);
    if (totalRows == 0) {
      return PageResult.createZeroRowResult(page);
    }

    List<WmsStockAvailableResult> stockAvailableList = wmsServiceMapper.WmsUsableStockList(page);

    return PageResult.create(page, totalRows, stockAvailableList);
  }
}
