package com.redescooter.ses.web.ros.service.wms.impl;

import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.WmsServiceMapper;
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
        return PageResult.create(enter, totalRows, wmsServiceMapper.wmsInWhList(enter));
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
        return PageResult.create(enter, totalRows, wmsServiceMapper.stockPendingList(enter));

    }

    /**
     * 查询入库详情对象
     *
     * @param enter
     * @return
     */
    @Override
    public WmsInWhDetailsResult getInWhDetails(WmsWhInDetailsEnter enter) {
        if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getType())) {
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
    public List<WmsProductListResult> getProductList(WmsWhInDetailsEnter enter) {
        if (SourceTypeEnums.ALLOCATE.getValue().equals(enter.getType())) {
            return wmsServiceMapper.partList(enter);
        } else {
            return wmsServiceMapper.productList(enter);
        }
    }
}
