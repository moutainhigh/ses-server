package com.redescooter.ses.web.ros.service.wms.cn.fr.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsMaterialStockMapper;
import com.redescooter.ses.web.ros.dm.OpeWmsCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsPartsStock;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeWmsCombinStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsPartsStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
import com.redescooter.ses.web.ros.service.wms.cn.fr.FrWhService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrTodayInOrOutStockCountResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/31 11:57
 */
@Service
@Slf4j
public class FrWhServiceImpl implements FrWhService {

    @Autowired
    private WmsFinishStockMapper wmsFinishStockMapper;

    @Autowired
    private WmsMaterialStockMapper wmsMaterialStockMapper;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opewmsCombinStockService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;


    /**
     *  出入库统计
     * @param enter
     * @return
     */
    @Override
    public List<FrTodayInOrOutStockCountResult> inOrOutCount(GeneralEnter enter) {
        List<FrTodayInOrOutStockCountResult> list = new ArrayList<>();
        // 今日入库
        FrTodayInOrOutStockCountResult inWh = new FrTodayInOrOutStockCountResult();
        inWh.setType(1);
        inWh.setScooterNum(wmsFinishStockMapper.frTodayScooterInOrOutStockCount(DateUtil.getDate(),1));
        inWh.setCombinNum(wmsFinishStockMapper.frTodayCombinInOrOutStockCount(DateUtil.getDate(),1));
        inWh.setPartsNum(wmsMaterialStockMapper.frTodayPartsInOrOutStockCount(DateUtil.getDate(),1));
        inWh.setCountNum(inWh.getScooterNum() + inWh.getCombinNum() + inWh.getPartsNum());
        list.add(inWh);

        // 今日出库
        FrTodayInOrOutStockCountResult outWh = new FrTodayInOrOutStockCountResult();
        outWh.setType(2);
        outWh.setScooterNum(wmsFinishStockMapper.frTodayScooterInOrOutStockCount(DateUtil.getDate(),2));
        outWh.setCombinNum(wmsFinishStockMapper.frTodayCombinInOrOutStockCount(DateUtil.getDate(),2));
        outWh.setPartsNum(wmsMaterialStockMapper.frTodayPartsInOrOutStockCount(DateUtil.getDate(),2));
        outWh.setCountNum(outWh.getScooterNum() + outWh.getCombinNum() + outWh.getPartsNum());
        list.add(outWh);
        return list;
    }

    /**
     *  库存统计
     * @param enter
     * @return
     */
    @Override
    public FrStockCountResult stockCount(GeneralEnter enter) {
        FrStockCountResult result = new FrStockCountResult();
        // 车辆
        QueryWrapper<OpeWmsScooterStock> scooter = new QueryWrapper<>();
        scooter.eq(OpeWmsScooterStock.COL_STOCK_TYPE,2);
        List<OpeWmsScooterStock> scooterStockList = opeWmsScooterStockService.list(scooter);
        result.setScooterNum(CollectionUtils.isEmpty(scooterStockList)?0:scooterStockList.stream().mapToInt(OpeWmsScooterStock::getAbleStockQty).sum());
        // 组装件
        QueryWrapper<OpeWmsCombinStock> comb = new QueryWrapper<>();
        comb.eq(OpeWmsCombinStock.COL_STOCK_TYPE,2);
        List<OpeWmsCombinStock> combStockList = opewmsCombinStockService.list(comb);
        result.setCombinNum(CollectionUtils.isEmpty(combStockList)?0:combStockList.stream().mapToInt(OpeWmsCombinStock::getAbleStockQty).sum());
        // 部件
        QueryWrapper<OpeWmsPartsStock> parts = new QueryWrapper<>();
        parts.eq(OpeWmsPartsStock.COL_STOCK_TYPE,2);
        List<OpeWmsPartsStock> partsStockList = opeWmsPartsStockService.list(parts);
        result.setPartsNum(CollectionUtils.isEmpty(partsStockList)?0:partsStockList.stream().mapToInt(OpeWmsPartsStock::getAbleStockQty).sum());
        return result;
    }


    @Override
    public PageResult<WmsFinishScooterListResult> frScooterList(WmsFinishScooterListEnter enter) {
        return null;
    }


    @Override
    public WmsfinishScooterDetailResult frScooterDetail(IdEnter enter) {
        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getById(enter.getId());
        if (scooterStock != null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        return new WmsfinishScooterDetailResult();
    }


    @Override
    public PageResult<WmsFinishCombinListResult> frCombinList(CombinationListEnter enter) {
        return null;
    }


    @Override
    public WmsfinishCombinDetailResult frCombinDetail(IdEnter enter) {
        OpeWmsCombinStock combinStock = opewmsCombinStockService.getById(enter.getId());
        if (combinStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        return new WmsfinishCombinDetailResult();
    }


    @Override
    public PageResult<MaterialStockPartsListResult> frPartsList(MaterialStockPartsListEnter enter) {
        return null;
    }


    @Override
    public MaterialpartsStockDetailResult frPartsDetail(IdEnter enter) {
        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getById(enter.getId());
        if (partsStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        return new MaterialpartsStockDetailResult();
    }



}
