package com.redescooter.ses.web.ros.service.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockTypeResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassNameWmsStockAvailableService
 * @Description
 * @Author Joan
 * @Date2020/7/17 10:07
 * @Version V1.0
 **/
public interface WmsStockService{
  /**
   * 库存类型
   *
   * @retrn
   */
  Map<String, String> getStockType(GeneralEnter enter);
  /**
   * 库存单状态统计
   *
   * @retrn
   */
  Map<String, Integer> countByType(GeneralEnter enter);


  /**
   * 查询仓储显示可用库存集合
   * @return
   */
  PageResult<WmsStockAvailableResult> getStockAvailableList(WmsStockEnter enter);
}
