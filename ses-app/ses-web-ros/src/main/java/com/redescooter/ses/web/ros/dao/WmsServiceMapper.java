package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.web.ros.vo.wms.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.WmsStockEnter;
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

}

