package com.redescooter.ses.web.ros.service.wms.cn;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsInWhDetailsResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsInWhResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsProductListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsWhInDetailsEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsWhInEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsWhInStockPendingResult;

import java.util.List;

/**
 * @ClassNameWmsWhInService
 * @Description
 * @Author Joan
 * @Date2020/7/20 10:29
 * @Version V1.0
 **/

public interface WmsWhInService{
  /**
   * 查询入库集合
   * @return
   */
  PageResult<WmsInWhResult> getWmsInWhList(WmsWhInEnter enter);

  /**
   * 查询入库库存待定结果集合
   * @return
   */
  PageResult<WmsWhInStockPendingResult> getWhInStockPendingList(WmsWhInEnter enter);


  /**
   * 入库单人员信息详情
   * @return
   */
  WmsInWhDetailsResult getInWhDetails(WmsWhInDetailsEnter enter);


  /**
   * 查询入库详情对象
   * @return
   */
  List<WmsProductListResult> getProductList(WmsWhInDetailsEnter enter);
}
