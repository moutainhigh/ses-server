package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.web.ros.vo.bom.ProdoctPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNamewmsServiceMapper
 * @Description
 * @Author Joan
 * @Date2020/7/16 18:30
 * @Version V1.0
 **/
public interface WmsServiceMapper {

  /**
   * 返回仓储可用列表count
   *
   * @param enter
   * @return
   */
  int wmsUsableStockCount(@Param("enter") WmsStockEnter enter);

  /**
   * 仓储可用列表
   *
   * @param enter
   * @return
   */
  List<WmsStockAvailableResult> wmsUsableStockList(@Param("enter") WmsStockEnter enter);

  /**
   * 返回已入库列表count
   *
   * @param enter
   * @return
   */
  int wmsInWhCount(@Param("enter") WmsWhInEnter enter);

  /**
   * 已入库列表
   *
   * @param enter
   * @return
   */
  List<WmsInWhResult> wmsInWhList(@Param("enter") WmsWhInEnter enter);


  /**
   * 返回待入库列表count
   *
   * @param enter
   * @return
   */
  int stockPendingCount(@Param("enter") WmsWhInEnter enter);

  /**
   * 待入库列表
   *
   * @param enter
   * @return
   */
  List<WmsWhInStockPendingResult> stockPendingList(@Param("enter") WmsWhInEnter enter);


  /**
   * 调拨单人员信息详情
   *
   * @param enter
   * @return
   */
  WmsInWhDetailsResult allocateDetails(@Param("enter") WmsWhInDetailsEnter enter);


  /**
   * 组装单人员信息详情
   *
   * @param enter
   * @return
   */
  WmsInWhDetailsResult assemblyDetails(@Param("enter") WmsWhInDetailsEnter enter);

  /**
   * 部件信息详情集合
   *
   * @param enter
   * @return
   */
  List<WmsProductListResult> partList(@Param("enter") WmsWhInDetailsEnter enter);

  /**
   * 产品信息详情集合
   *
   * @param enter
   * @return
   */
  List<WmsProductListResult> productList(@Param("enter") WmsWhInDetailsEnter enter);
}

