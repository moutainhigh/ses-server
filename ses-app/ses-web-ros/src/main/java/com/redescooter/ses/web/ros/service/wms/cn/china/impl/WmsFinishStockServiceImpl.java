package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dm.OpeWmsCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeWmsCombinStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsFinishStockService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 成品库实现类
 * @author: Aleks
 * @create: 2020/12/28 15:00
 */
@Service
@Slf4j
public class WmsFinishStockServiceImpl implements WmsFinishStockService {

    @Autowired
    private WmsFinishStockMapper wmsFinishStockMapper;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    /**
     * 成品库车辆库存列表
     * @param enter
     * @return
     */
    @Override
    public PageResult<WmsFinishScooterListResult> finishScooterList(WmsFinishScooterListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsFinishStockMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsFinishScooterListResult> list = wmsFinishStockMapper.finishScooterList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    /**
     * 成品库车辆库存详情
     * @param enter
     * @return
     */
    @Override
    public WmsfinishScooterDetailResult finishScooterDetail(IdEnter enter) {
        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getById(enter.getId());
        if (scooterStock == null){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishScooterDetailResult result = wmsFinishStockMapper.finishScooterDetail(enter.getId());
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    /**
     * 库存统计
     * @param enter
     * @return
     */
    @Override
    public WmsStockCountResult wmsStockCount(WmsStockCountEnter enter) {
        WmsStockCountResult result = new WmsStockCountResult();
        switch (enter.getProductType()){
            case 1:
                //车辆
                result = wmsFinishStockMapper.wmsScooterStockCount(enter.getStockType());
            default:
                break;
            case 2:
                // 组装件
                result = wmsFinishStockMapper.wmsCombinStockCount(enter.getStockType());
                break;
            case 3:
                // 部件
                result = wmsFinishStockMapper.wmsPartsStockCount(enter.getStockType());
                break;
        }
        return result;
    }


    /**
     * 成品库tab的数量统计（只有车辆和组装件）
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> finishStockTabCount(WmsStockCountEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 车辆
        QueryWrapper<OpeWmsScooterStock> scooter = new QueryWrapper<>();
        scooter.eq(OpeWmsScooterStock.COL_STOCK_TYPE,enter.getStockType());
        map.put("1",opeWmsScooterStockService.count(scooter));

        // 组装件
        QueryWrapper<OpeWmsCombinStock> combin = new QueryWrapper<>();
        combin.eq(OpeWmsCombinStock.COL_STOCK_TYPE,enter.getStockType());
        map.put("2",opeWmsCombinStockService.count(combin));
        return map;
    }


    /**
     * 剩下的原料（部件）还可生产多少车
     * @param enter
     * @return
     */
    @Override
    public List<AbleProductionScooterResult> ableProductionScooter(GeneralEnter enter) {
        List<AbleProductionScooterResult> list = new ArrayList<>();
        list.add(new AbleProductionScooterResult());
        // todo 最后写
        return list;
    }


    /**
     * 成品库组装件list
     * @param enter
     * @return
     */
    @Override
    public PageResult<WmsFinishCombinListResult> finishCombinList(CombinationListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsFinishStockMapper.combinCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsFinishCombinListResult> list = wmsFinishStockMapper.finishCombinList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    /**
     * 成品库组装件详情
     * @param enter
     * @return
     */
    @Override
    public WmsfinishCombinDetailResult finishCombinDetail(IdEnter enter) {
        OpeWmsCombinStock combinStock = opeWmsCombinStockService.getById(enter.getId());
        if (combinStock == null){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishCombinDetailResult result = wmsFinishStockMapper.finishCombinDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }
}
