package com.redescooter.ses.web.ros.service.wms;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.wms.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.WmsStockEnter;

/**
 * @ClassNameWmsStockAvailableService
 * @Description
 * @Author Joan
 * @Date2020/7/17 10:07
 * @Version V1.0
 **/
public interface WmsStockService{

  /**
   * 查询仓储显示可用库存集合
   * @return
   */
  PageResult<WmsStockAvailableResult> getStockAvailableList(WmsStockEnter enter);
}