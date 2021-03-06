package com.redescooter.ses.web.ros.dao.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.vo.wms.cn.*;
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
  int wmsUsableStockCount(@Param("enter") WmsStockEnter enter, @Param("purchasWhId") Long purchasWhId, @Param("assemblyWhId") Long assemblyId, @Param("commonTypeList") List<String> commonTypeList,@Param("amountType") String amountType);

  /**
   * 仓储可用列表
   *
   * @param enter
   * @return
   */
  List<WmsStockAvailableResult> wmsUsableStockList(@Param("enter") WmsStockEnter enter, @Param("purchasWhId") Long purchasWhId, @Param("assemblyWhId") Long assemblyId, @Param("commonTypeList") List<String> commonTypeList,@Param("amountType") String amountType);

  /**
   * 返回仓储待生产列表count
   *
   * @param enter
   * @return
   */
  int wmsBePredictedStockCount(@Param("enter") WmsStockEnter enter, @Param("whseid") long whseid, @Param("commonTypeList") List<String> commonTypeList, @Param("amountType") String amountType);

  /**
   * 仓储待生产列表
   *
   * @param enter
   * @return
   */
  List<WmsStockAvailableResult> wmsBePredictedStockList(@Param("enter") WmsStockEnter enter, @Param("whseid") long whseid, @Param("commonTypeList") List<String> commonTypeList, @Param("amountType") String amountType);

  /**
   * 返回仓储待入库列表count
   *
   * @param enter
   * @return
   */
  int wmsStoredStockCount(@Param("enter") WmsStockEnter enter, @Param("allocatewhId") Long allocatewhId, @Param("assemblyWhId") Long assemblyWhId, @Param("commonTypeList") List<String> commonTypeList,
                          @Param("amountType") String amountType);

  /**
   * 仓储待入库列表
   *
   * @param enter
   * @return
   */
  List<WmsStockAvailableResult> wmsStoredStockList(@Param("enter") WmsStockEnter enter, @Param("allocatewhId") Long allocatewhId, @Param("assemblyWhId") Long assemblyWhId, @Param("commonTypeList") List<String> commonTypeList, @Param("amountType") String amountType);

  /**
   * 返回仓储出库列表count
   *
   * @param enter
   * @return
   */
  int wmsOutWhStockCount(@Param("enter") WmsStockEnter enter, @Param("amountType") String amountType);

  /**
   * 返回仓储出库列表count
   *
   * @param enter
   * @return
   */
  List<WmsStockAvailableResult> wmsOutWhStockList(@Param("enter") WmsStockEnter enter, @Param("amountType") String amountType);


  /**
   * 返回已入库列表count
   *
   * @param enter
   * @return
   */
  int wmsInWhCountByType(@Param("enter") GeneralEnter enter, @Param("inWhStatus") String inWhStatus, @Param("assemblingStatus") String assemblingStatus);

  /**
   * 返回已入库列表count
   *
   * @param enter
   * @return
   */
  int stockPendingCountByType(@Param("enter") GeneralEnter enter, @Param("inWhStatus") String inWhStatus, @Param("assemblingStatus") String assemblingStatus);


  /**
   * 返回已入库列表count
   *
   * @param enter
   * @return
   */
  int wmsInWhCount(@Param("enter") WmsWhInEnter enter, @Param("inWhStatus") String inWhStatus, @Param("assemblingStatus") String assemblingStatus);

  /**
   * 已入库列表
   *
   * @param enter
   * @return
   */
  List<WmsInWhResult> wmsInWhList(@Param("enter") WmsWhInEnter enter, @Param("inWhStatus") String inWhStatus, @Param("assemblingStatus") String assemblingStatus);


  /**
   * 返回待入库列表count
   *
   * @param enter
   * @return
   */
  int stockPendingCount(@Param("enter") WmsWhInEnter enter, @Param("inWhStatus") String inWhStatus, @Param("assemblingStatus") String assemblingStatus);

  /**
   * 待入库列表
   *
   * @param enter
   * @return
   */
  List<WmsInWhResult> stockPendingList(@Param("enter") WmsWhInEnter enter, @Param("inWhStatus") String inWhStatus, @Param("assemblingStatus") String assemblingStatus);


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

  /**
   * 可用库存count
   *
   * @param
   * @return
   */
  int usableStockCountByType( @Param("purchasWhId") Long purchasWhId, @Param("assemblyWhId") Long assemblyWhId, @Param("commonTypeList") List<String> commonTypeList,
                              @Param("amountType") String amountType);

  /**
   * 返回仓储待入库列表count
   *
   * @param
   * @return
   */
  int wmsStoredStockCountByType(@Param("enter") GeneralEnter enter, @Param("allocatewhId") Long allocatewhId, @Param("assemblyWhId") Long assemblyWhId, @Param("commonTypeList") List<String> commonTypeList,
                                @Param("amountType") String amountType);

  /**
   * 返回仓储待生产列表count
   *
   * @param
   * @return
   */
  int wmsBePredictedStockCountByType(@Param("whseid") long whseid, @Param("commonTypeList") List<String> commonTypeList, @Param("amountType") String amountType);


  /**
   * 返回仓储出库列表count
   *
   * @param
   * @return
   */
  int wmsOutWhStockCountByType(@Param("status") String status, @Param("commonTypeList") List<String> commonTypeList, @Param("amountType") String amountType);

}

