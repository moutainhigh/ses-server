package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsMaterialStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsQualifiedMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.china.ChinaWhService;
import com.redescooter.ses.web.ros.vo.wms.cn.china.ChinaInOrOutCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.StockCountResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:ChinaWhServiceImpl
 * @description: ChinaWhServiceImpl
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/27 16:09
 */
@Service
public class ChinaWhServiceImpl implements ChinaWhService {

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private OpeWmsQualifiedScooterStockService opeWmsQualifiedScooterStockService;

    @Autowired
    private OpeWmsQualifiedCombinStockService opeWmsQualifiedCombinStockService;

    @Autowired
    private OpeWmsQualifiedPartsStockService opeWmsQualifiedPartsStockService;

    @Autowired
    private WmsFinishStockMapper wmsFinishStockMapper;

    @Autowired
    private WmsMaterialStockMapper wmsMaterialStockMapper;

    @Autowired
    private WmsQualifiedMapper wmsQualifiedMapper;


    @Override
    public List<ChinaInOrOutCountResult> inOrOutCount(GeneralEnter enter) {
        List<ChinaInOrOutCountResult> list = new ArrayList<>();
        // 今日入库
        ChinaInOrOutCountResult inWh = new ChinaInOrOutCountResult();
        inWh.setType(1);
        inWh.setFinishWhNum(wmsFinishStockMapper.finishTodayStackCount(DateUtil.getDate(), 1));
        inWh.setMaterialWhNum(wmsMaterialStockMapper.materialTodayStockCount(DateUtil.getDate(), 1));
        inWh.setUnqualifiedWhNum(wmsQualifiedMapper.qualifiedTodayStockCount(DateUtil.getDate(), 1));
        inWh.setCountNum((inWh.getFinishWhNum() == null ? 0 : inWh.getFinishWhNum()) + (inWh.getMaterialWhNum() == null ? 0 : inWh.getMaterialWhNum()) + (inWh.getUnqualifiedWhNum() == null ? 0 :
                inWh.getUnqualifiedWhNum()));
        list.add(inWh);

        // 今日出库
        ChinaInOrOutCountResult outWh = new ChinaInOrOutCountResult();
        outWh.setType(2);
        outWh.setFinishWhNum(wmsFinishStockMapper.finishTodayStackCount(DateUtil.getDate(), 2));
        outWh.setMaterialWhNum(wmsMaterialStockMapper.materialTodayStockCount(DateUtil.getDate(), 2));
        outWh.setUnqualifiedWhNum(wmsQualifiedMapper.qualifiedTodayStockCount(DateUtil.getDate(), 2));
        outWh.setCountNum((outWh.getFinishWhNum() == null ? 0 : outWh.getFinishWhNum()) + (outWh.getMaterialWhNum() == null ? 0 : outWh.getMaterialWhNum()) + (outWh.getUnqualifiedWhNum() == null ? 0 : outWh.getUnqualifiedWhNum()));
        list.add(outWh);
        return list;
    }


    @Override
    public List<StockCountResult> stockCount(GeneralEnter enter) {
        List<StockCountResult> list = new ArrayList<>();
        // 成品库(车辆和组装件)
        StockCountResult finishWh = new StockCountResult();
        finishWh.setType(1);
        // 车辆库存
        QueryWrapper<OpeWmsScooterStock> scooter = new QueryWrapper<>();
        scooter.eq(OpeWmsScooterStock.COL_STOCK_TYPE, 1);
        List<OpeWmsScooterStock> scooterStockList = opeWmsScooterStockService.list(scooter);
        if (CollectionUtils.isNotEmpty(scooterStockList)) {
            finishWh.setScooterNum(scooterStockList.stream().mapToInt(OpeWmsScooterStock::getAbleStockQty).sum());
        } else {
            finishWh.setScooterNum(0);
        }
        // 组装件库存
        QueryWrapper<OpeWmsCombinStock> combin = new QueryWrapper<>();
        combin.eq(OpeWmsCombinStock.COL_STOCK_TYPE, 1);
        List<OpeWmsCombinStock> combinStockList = opeWmsCombinStockService.list(combin);
        if (CollectionUtils.isNotEmpty(scooterStockList)) {
            finishWh.setCombinNum(combinStockList.stream().mapToInt(OpeWmsCombinStock::getAbleStockQty).sum());
        } else {
            finishWh.setCombinNum(0);
        }
        finishWh.setCountNum((finishWh.getScooterNum() == null ? 0 : finishWh.getScooterNum()) + (finishWh.getCombinNum() == null ? 0 : finishWh.getCombinNum()));
        list.add(finishWh);

        // 原料库(只有部件)
        StockCountResult materialWh = new StockCountResult();
        materialWh.setType(2);
        QueryWrapper<OpeWmsPartsStock> parts = new QueryWrapper<>();
        parts.eq(OpeWmsPartsStock.COL_STOCK_TYPE, 1);
        List<OpeWmsPartsStock> partsStockList = opeWmsPartsStockService.list(parts);
        if (CollectionUtils.isNotEmpty(partsStockList)) {
            materialWh.setPartsNum(partsStockList.stream().mapToInt(OpeWmsPartsStock::getAbleStockQty).sum());
        } else {
            materialWh.setPartsNum(0);
        }
        materialWh.setCountNum(materialWh.getPartsNum());
        list.add(materialWh);

        // 不合格品库(车辆，组装件，部件)
        StockCountResult unqualifiedWh = new StockCountResult();
        unqualifiedWh.setType(3);
        // 车辆
        List<OpeWmsQualifiedScooterStock> scooterStocks = opeWmsQualifiedScooterStockService.list();
        if (CollectionUtils.isNotEmpty(scooterStocks)) {
            unqualifiedWh.setScooterNum(scooterStocks.stream().mapToInt(OpeWmsQualifiedScooterStock::getQty).sum());
        } else {
            unqualifiedWh.setScooterNum(0);
        }
        // 组装件
        List<OpeWmsQualifiedCombinStock> combinStocks = opeWmsQualifiedCombinStockService.list();
        if (CollectionUtils.isNotEmpty(combinStocks)) {
            unqualifiedWh.setCombinNum(combinStocks.stream().mapToInt(OpeWmsQualifiedCombinStock::getQty).sum());
        } else {
            unqualifiedWh.setCombinNum(0);
        }
        // 部件
        List<OpeWmsQualifiedPartsStock> partsStocks = opeWmsQualifiedPartsStockService.list();
        if (CollectionUtils.isNotEmpty(partsStocks)) {
            unqualifiedWh.setPartsNum(partsStocks.stream().mapToInt(OpeWmsQualifiedPartsStock::getQty).sum());
        } else {
            unqualifiedWh.setPartsNum(0);
        }
        unqualifiedWh.setCountNum((unqualifiedWh.getScooterNum() == null ? 0 : unqualifiedWh.getScooterNum()) + (unqualifiedWh.getCombinNum() == null ? 0 : unqualifiedWh.getCombinNum()) + (unqualifiedWh.getPartsNum() == null ? 0 : unqualifiedWh.getPartsNum()));
        list.add(unqualifiedWh);
        return list;
    }
}
