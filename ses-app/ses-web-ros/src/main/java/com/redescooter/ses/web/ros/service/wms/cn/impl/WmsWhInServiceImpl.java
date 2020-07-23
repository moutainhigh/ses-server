package com.redescooter.ses.web.ros.service.wms.cn.impl;

import com.redescooter.ses.api.common.enums.production.ProductionTypeEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PushTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.wms.cn.WmsServiceMapper;
import com.redescooter.ses.web.ros.service.wms.cn.WmsWhInService;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsInWhDetailsResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsInWhResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsProductListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsWhInDetailsEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsWhInEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * 入库单状态统计
   *
   * @param enter
   * @retrn
   */
  @Override
  public Map<String, Integer> countByType(GeneralEnter enter) {
    Map<String, Integer> map = new HashMap<>();
    int wmsInWhCount = wmsServiceMapper.wmsInWhCountByType(enter);
    int stockPendingCount = wmsServiceMapper.stockPendingCountByType(enter);
    map.put(String.valueOf(PushTypeEnums.TAG.getValue()),wmsInWhCount);
    map.put(String.valueOf(PushTypeEnums.TAG_AND.getValue()),stockPendingCount);
    return map;
  }

  /**
     * 查询入库集合
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WmsInWhResult> list(WmsWhInEnter enter) {
      if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
        return PageResult.createZeroRowResult(enter);
      }
      int totalRows = 0;
      List<WmsInWhResult> wmsInWhResult = new ArrayList<WmsInWhResult>();
      if (StringUtils.equals(enter.getClassType(), ProductionTypeEnums.TODO.getValue())) {
          totalRows = wmsServiceMapper.wmsInWhCount(enter);
          wmsInWhResult= wmsServiceMapper.wmsInWhList(enter);
      } else {

         totalRows = wmsServiceMapper.stockPendingCount(enter);
         wmsInWhResult= wmsServiceMapper.stockPendingList(enter);
      }
      if (totalRows == 0) {
        return PageResult.createZeroRowResult(enter);
      }
      return PageResult.create(enter, totalRows,wmsInWhResult);
    }

    /**
     * 查询入库详情对象
     *
     * @param enter
     * @return
     */
    @Override
    public WmsInWhDetailsResult details(WmsWhInDetailsEnter enter) {
        if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getProductType())) {
            return wmsServiceMapper.allocateDetails(enter);
        } else {
            return wmsServiceMapper.assemblyDetails(enter);
        }
    }

    /**
     * 查询入库详情对象
     *
     * @param enter
     * @return
     */
    @Override
    public List<WmsProductListResult> productList(WmsWhInDetailsEnter enter) {
        if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getProductType())) {
            return wmsServiceMapper.partList(enter);
        } else {
            return wmsServiceMapper.productList(enter);
        }
    }
}
