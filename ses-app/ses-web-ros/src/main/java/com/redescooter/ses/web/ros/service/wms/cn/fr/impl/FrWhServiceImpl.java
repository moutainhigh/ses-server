package com.redescooter.ses.web.ros.service.wms.cn.fr.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.dao.wms.cn.china.OpeWmsStockSerialNumberMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsMaterialStockMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeWmsCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsPartsStock;
import com.redescooter.ses.web.ros.dm.OpeWmsQualifiedScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeWmsCombinStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsPartsStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsQualifiedScooterStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
import com.redescooter.ses.web.ros.service.wms.cn.fr.FrWhService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialpartsStockDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsDetailEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishCombinListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockCountEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockRecordResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishCombinDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishScooterDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrTodayInOrOutStockCountResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private OpeWmsQualifiedScooterStockService opeWmsQualifiedScooterStockService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeWmsStockSerialNumberMapper opeWmsStockSerialNumberMapper;


    /**
     * 出入库统计
     *
     * @param enter
     * @return
     */
    @Override
    public List<FrTodayInOrOutStockCountResult> inOrOutCount(GeneralEnter enter) {
        List<FrTodayInOrOutStockCountResult> list = new ArrayList<>();
        // 今日入库
        FrTodayInOrOutStockCountResult inWh = new FrTodayInOrOutStockCountResult();
        inWh.setType(1);
        inWh.setScooterNum(wmsFinishStockMapper.frTodayScooterInOrOutStockCount(DateUtil.getDate(), 1));
        inWh.setCombinNum(wmsFinishStockMapper.frTodayCombinInOrOutStockCount(DateUtil.getDate(), 1));
        inWh.setPartsNum(wmsMaterialStockMapper.frTodayPartsInOrOutStockCount(DateUtil.getDate(), 1));
        inWh.setCountNum(inWh.getScooterNum() + inWh.getCombinNum() + inWh.getPartsNum());
        list.add(inWh);

        // 今日出库
        FrTodayInOrOutStockCountResult outWh = new FrTodayInOrOutStockCountResult();
        outWh.setType(2);
        outWh.setScooterNum(wmsFinishStockMapper.frTodayScooterInOrOutStockCount(DateUtil.getDate(), 2));
        outWh.setCombinNum(wmsFinishStockMapper.frTodayCombinInOrOutStockCount(DateUtil.getDate(), 2));
        outWh.setPartsNum(wmsMaterialStockMapper.frTodayPartsInOrOutStockCount(DateUtil.getDate(), 2));
        outWh.setCountNum(outWh.getScooterNum() + outWh.getCombinNum() + outWh.getPartsNum());
        list.add(outWh);
        return list;
    }

    /**
     * 库存统计
     *
     * @param enter
     * @return
     */
    @Override
    public FrStockCountResult stockCount(GeneralEnter enter) {
        FrStockCountResult result = new FrStockCountResult();
        // 车辆
        QueryWrapper<OpeWmsScooterStock> scooter = new QueryWrapper<>();
        scooter.eq(OpeWmsScooterStock.COL_STOCK_TYPE, 2);
        List<OpeWmsScooterStock> scooterStockList = opeWmsScooterStockService.list(scooter);
        result.setScooterNum(CollectionUtils.isEmpty(scooterStockList) ? 0 : scooterStockList.stream().mapToInt(OpeWmsScooterStock::getAbleStockQty).sum());
        // 组装件
        QueryWrapper<OpeWmsCombinStock> comb = new QueryWrapper<>();
        comb.eq(OpeWmsCombinStock.COL_STOCK_TYPE, 2);
        List<OpeWmsCombinStock> combStockList = opeWmsCombinStockService.list(comb);
        result.setCombinNum(CollectionUtils.isEmpty(combStockList) ? 0 : combStockList.stream().mapToInt(OpeWmsCombinStock::getAbleStockQty).sum());
        // 部件
        QueryWrapper<OpeWmsPartsStock> parts = new QueryWrapper<>();
        parts.eq(OpeWmsPartsStock.COL_STOCK_TYPE, 2);
        List<OpeWmsPartsStock> partsStockList = opeWmsPartsStockService.list(parts);
        result.setPartsNum(CollectionUtils.isEmpty(partsStockList) ? 0 : partsStockList.stream().mapToInt(OpeWmsPartsStock::getAbleStockQty).sum());
        return result;
    }


    /**
     * 法国仓库车辆库存列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WmsFinishScooterListResult> frScooterList(WmsFinishScooterListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsFinishStockMapper.frScooterTotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsFinishScooterListResult> list = wmsFinishStockMapper.frScooterList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    /**
     * 法国仓库车辆库存详情
     *
     * @param enter
     * @return
     */
    @Override
    public WmsfinishScooterDetailResult frScooterDetail(IdEnter enter) {
        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getById(enter.getId());
        if (scooterStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishScooterDetailResult result = wmsFinishStockMapper.finishScooterDetail(enter.getId());
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    /**
     * 法国组装件库存列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WmsFinishCombinListResult> frCombinList(CombinationListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsFinishStockMapper.combinCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsFinishCombinListResult> list = wmsFinishStockMapper.finishCombinList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    /**
     * 法国组装件库存详情
     *
     * @param enter
     * @return
     */
    @Override
    public WmsfinishCombinDetailResult frCombinDetail(IdEnter enter) {
        OpeWmsCombinStock combinStock = opeWmsCombinStockService.getById(enter.getId());
        if (combinStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishCombinDetailResult result = wmsFinishStockMapper.finishCombinDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    /**
     * 法国部件的库存列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialStockPartsListResult> frPartsList(MaterialStockPartsListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsMaterialStockMapper.frPartsCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<MaterialStockPartsListResult> list = wmsMaterialStockMapper.frPartsList(enter);
        if (CollectionUtils.isNotEmpty(list)) {
            for (MaterialStockPartsListResult model : list) {
                Long partsId = model.getPartsId();
                if (null != partsId) {
                    OpeProductionParts parts = opeProductionPartsService.getById(partsId);
                    if (null != parts) {
                        Integer idClass = parts.getIdCalss();
                        model.setIdClass(idClass);
                    }
                }
            }
        }
        return PageResult.create(enter, totalRows, list);
    }


    /**
     * 法国部件的库存详情
     *
     * @param enter
     * @return
     */
    @Override
    public MaterialpartsStockDetailResult frPartsDetail(IdEnter enter) {
        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getById(enter.getId());
        if (partsStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        MaterialpartsStockDetailResult result = wmsMaterialStockMapper.materialStockPartsDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        if (CollectionUtils.isNotEmpty(record)) {
            for (WmsStockRecordResult r : record) {
                LambdaQueryWrapper<OpeWmsStockSerialNumber> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeWmsStockSerialNumber::getDr, DelStatusEnum.VALID.getCode());
                wrapper.eq(OpeWmsStockSerialNumber::getStockType, 2);
                wrapper.eq(OpeWmsStockSerialNumber::getRelationType, 3);
                wrapper.eq(OpeWmsStockSerialNumber::getRelationId, r.getRelationId());
                List<OpeWmsStockSerialNumber> list = opeWmsStockSerialNumberMapper.selectList(wrapper);
                if (CollectionUtils.isNotEmpty(list)) {
                    OpeWmsStockSerialNumber number = list.get(0);
                    String lotNum = number.getLotNum();
                    if (StringUtils.isNotBlank(lotNum)) {
                        r.setLotNum(lotNum);
                    }
                }
            }
        }
        result.setRecordList(record);
        return result;
    }


    /**
     * 新建出库单时，计算同车型/颜色的车辆可用库存时多少（出库数量要小于库存数）
     *
     * @param enter
     * @param whtype 1:中国仓库，2：法国仓库
     * @return
     */
    @Override
    public IntResult scooterNum(WmsFinishScooterListEnter enter, Integer whtype) {
        int num = 0;
        if (enter.getGroupId() != null && enter.getColorId() != null) {
            switch (enter.getSource()) {
                case 0:
                    QueryWrapper<OpeWmsScooterStock> qw = new QueryWrapper<>();
                    qw.eq(OpeWmsScooterStock.COL_GROUP_ID, enter.getGroupId());
                    qw.eq(OpeWmsScooterStock.COL_COLOR_ID, enter.getColorId());
                    qw.eq(OpeWmsScooterStock.COL_STOCK_TYPE, whtype);
                    List<OpeWmsScooterStock> list = opeWmsScooterStockService.list(qw);
                    if (CollectionUtils.isNotEmpty(list)) {
                        num = list.stream().mapToInt(OpeWmsScooterStock::getAbleStockQty).sum();
                    }
                default:
                    break;
                case 1:
                    // 说明是不合格品库正常创建出库单 可出库的数量来源与不合格品库的整车数量
                    QueryWrapper<OpeWmsQualifiedScooterStock> qualifiedScooterStockQueryWrapper = new QueryWrapper<>();
                    qualifiedScooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_COLOR_ID, enter.getColorId());
                    qualifiedScooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_GROUP_ID, enter.getGroupId());
                    qualifiedScooterStockQueryWrapper.last("limit 1");
                    OpeWmsQualifiedScooterStock qualifiedScooterStock = opeWmsQualifiedScooterStockService.getOne(qualifiedScooterStockQueryWrapper);
                    if (qualifiedScooterStock != null) {
                        num = qualifiedScooterStock.getQty();
                    }
                    break;
            }
        }

        return new IntResult(num);
    }

    @Override
    public Map<String, Integer> stockTabCount(WmsStockCountEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 车辆
        QueryWrapper<OpeWmsScooterStock> scooter = new QueryWrapper<>();
        scooter.eq(OpeWmsScooterStock.COL_STOCK_TYPE, enter.getStockType());
        map.put("1", opeWmsScooterStockService.count(scooter));

        // 组装件
        QueryWrapper<OpeWmsCombinStock> combin = new QueryWrapper<>();
        combin.eq(OpeWmsCombinStock.COL_STOCK_TYPE, enter.getStockType());
        map.put("2", opeWmsCombinStockService.count(combin));

        // 部件
        QueryWrapper<OpeWmsPartsStock> parts = new QueryWrapper<>();
        parts.eq(OpeWmsPartsStock.COL_STOCK_TYPE, enter.getStockType());
        map.put("3", opeWmsPartsStockService.count(parts));
        return map;
    }

    /**
     * 法国仓库车辆,组装件和部件详情
     */
    @Override
    public PageResult<WmsDetailResult> getDetail(WmsDetailEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int count = wmsFinishStockMapper.getDetailCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsDetailResult> list = wmsFinishStockMapper.getDetail(enter);
        return PageResult.create(enter, count, list);
    }

}
