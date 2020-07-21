package com.redescooter.ses.web.ros.service.wms.impl;

import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.WmsServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeAssemblyBOrderMapper;
import com.redescooter.ses.web.ros.dao.base.OpePartsMapper;
import com.redescooter.ses.web.ros.dao.base.OpePartsProductMapper;
import com.redescooter.ses.web.ros.service.base.OpeAllocateBService;
import com.redescooter.ses.web.ros.service.wms.WmsWhInService;
import com.redescooter.ses.web.ros.vo.wms.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameWmsWhInServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/7/20 13:26
 * @Version V1.0
 **/
@Service
@Slf4j
public class WmsWhInServiceImpl implements WmsWhInService {
  @Autowired
  private WmsServiceMapper wmsServiceMapper;
  /**
   * 查询入库集合
   *
   * @param enter
   * @return
   */
  @Override
  public PageResult<WmsInWhResult> getWmsInWhList(WmsWhInEnter enter) {
    if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
      return PageResult.createZeroRowResult(enter);
    }
    int totalRows = wmsServiceMapper.wmsInWhCount(enter);
    if (totalRows == 0) {
      return PageResult.createZeroRowResult(enter);
    }

    List<WmsInWhResult> wmsInWhResults = wmsServiceMapper.wmsInWhList(enter);

    return PageResult.create(enter, totalRows, wmsInWhResults);
  }

  /**
   * 查询入库库存待定结果集合
   *
   * @param enter
   * @return
   */
  @Override
  public PageResult<WmsWhInStockPendingResult> getWhInStockPendingList(WmsWhInEnter enter) {
    if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
      return PageResult.createZeroRowResult(enter);
    }
    int totalRows = wmsServiceMapper.stockPendingCount(enter);
    if (totalRows == 0) {
      return PageResult.createZeroRowResult(enter);
    }

    List<WmsWhInStockPendingResult> wmsWhInStockPendingResults = wmsServiceMapper.stockPendingList(enter);

    return PageResult.create(enter, totalRows, wmsWhInStockPendingResults);

  }

  /**
   * 查询入库详情对象
   *
   * @param enter
   * @return
   */
  @Override
  public WmsInWhDetailsResult getInWhDetails(WmsWhInDetailsEnter enter) {
    WmsInWhDetailsResult result = new WmsInWhDetailsResult();
    if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getType())) {
      result = wmsServiceMapper.allocateDetails(enter);
    } else {
      result = wmsServiceMapper.assemblyDetails(enter);
    }
    return result;
  }

  /**
   * 查询入库详情对象
   *
   * @param enter
   * @return
   */
  @Override
  public List<WmsProductListResult> getProductList(WmsWhInDetailsEnter enter) {
    List<WmsProductListResult> list = new ArrayList<WmsProductListResult>();
    if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getType())) {
      list = wmsServiceMapper.partList(enter);
    } else {
      list = wmsServiceMapper.productList(enter);
    }
    return list;
  }


}
