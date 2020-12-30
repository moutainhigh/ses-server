package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsQualifiedMapper;
import com.redescooter.ses.web.ros.dm.OpeWmsQualifiedCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsQualifiedPartsStock;
import com.redescooter.ses.web.ros.dm.OpeWmsQualifiedScooterStock;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeWmsQualifiedCombinStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsQualifiedPartsStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsQualifiedScooterStockService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsQualifiedService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 11:35
 */
@Service
public class WmsQualifiedServiceImpl implements WmsQualifiedService {

    @Autowired
    private WmsQualifiedMapper wmsQualifiedMapper;

    @Autowired
    private OpeWmsQualifiedScooterStockService opeWmsQualifiedScooterStockService;

    @Autowired
    private OpeWmsQualifiedCombinStockService opeWmsQualifiedCombinStockService;

    @Autowired
    private OpeWmsQualifiedPartsStockService opeWmsQualifiedPartsStockService;

    @Autowired
    private WmsFinishStockMapper wmsFinishStockMapper;


    @Override
    public PageResult<WmsQualifiedScooterListResult> scooterList(WmsFinishScooterListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsQualifiedMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsQualifiedScooterListResult> list = wmsQualifiedMapper.scooterList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public WmsfinishScooterDetailResult scooterDetail(IdEnter enter) {
        OpeWmsQualifiedScooterStock scooterStock = opeWmsQualifiedScooterStockService.getById(enter.getId());
        if (scooterStock == null){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishScooterDetailResult result = wmsQualifiedMapper.scooterDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    @Override
    public PageResult<WmsQualifiedCombinListResult> combineList(CombinationListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsQualifiedMapper.combinCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsQualifiedCombinListResult> list = wmsQualifiedMapper.combinList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public WmsfinishCombinDetailResult combinDetail(IdEnter enter) {
        OpeWmsQualifiedCombinStock combinStock = opeWmsQualifiedCombinStockService.getById(enter.getId());
        if (combinStock == null){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishCombinDetailResult result = wmsQualifiedMapper.combinDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    @Override
    public PageResult<WmsQualifiedPartsListResult> partsList(MaterialStockPartsListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsQualifiedMapper.partsCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsQualifiedPartsListResult> list = wmsQualifiedMapper.partsList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public MaterialpartsStockDetailResult partsDetail(IdEnter enter) {
        OpeWmsQualifiedPartsStock partsStock = opeWmsQualifiedPartsStockService.getById(enter.getId());
        if (partsStock == null){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        MaterialpartsStockDetailResult result = wmsQualifiedMapper.partsDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }
}
