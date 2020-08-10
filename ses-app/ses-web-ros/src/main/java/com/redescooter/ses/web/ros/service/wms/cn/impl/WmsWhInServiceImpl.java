package com.redescooter.ses.web.ros.service.wms.cn.impl;

import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WhInTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.wms.cn.WmsServiceMapper;
import com.redescooter.ses.web.ros.service.wms.cn.WmsWhInService;
import com.redescooter.ses.web.ros.vo.wms.cn.*;
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
    //已入库数据统计
    int wmsInWhCount = wmsServiceMapper.wmsInWhCountByType(enter,AllocateOrderStatusEnums.INPRODUCTIONWH.getValue(), AssemblyStatusEnums.ASSEMBLING.getValue());
    map.put(String.valueOf(WhInTypeEnums.TODO.getValue()),wmsInWhCount);
    //待入库数据统计
    int stockPendingCount = wmsServiceMapper.stockPendingCountByType(enter,AllocateOrderStatusEnums.ALLOCATE.getValue(), AssemblyStatusEnums.QC_PASSED.getValue());
    map.put(String.valueOf(WhInTypeEnums.HISTORY.getValue()),stockPendingCount);
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

      //已入库列表
      if (StringUtils.equals(enter.getClassType(), WhInTypeEnums.TODO.getValue())) {
          totalRows = wmsServiceMapper.wmsInWhCount(enter,AllocateOrderStatusEnums.INPRODUCTIONWH.getValue(), AssemblyStatusEnums.ASSEMBLING.getValue());
          wmsInWhResult= wmsServiceMapper.wmsInWhList(enter,AllocateOrderStatusEnums.INPRODUCTIONWH.getValue(), AssemblyStatusEnums.ASSEMBLING.getValue());
      }

      //待入库列表
      if (StringUtils.equals(enter.getClassType(), WhInTypeEnums.HISTORY.getValue())){
         totalRows = wmsServiceMapper.stockPendingCount(enter,AllocateOrderStatusEnums.ALLOCATE.getValue(), AssemblyStatusEnums.QC_PASSED.getValue());
         wmsInWhResult= wmsServiceMapper.stockPendingList(enter,AllocateOrderStatusEnums.ALLOCATE.getValue(), AssemblyStatusEnums.QC_PASSED.getValue());
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
        if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getDocumentType())) {
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
        if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getDocumentType())) {
          List<WmsProductListResult> partList = wmsServiceMapper.partList(enter);
            partList.forEach(item ->{
                item.setProductType(BomCommonTypeEnums.getValueByCode(item.getProductType());
              });
            return partList;
        } else {
            return wmsServiceMapper.productList(enter);
        }
    }
}
