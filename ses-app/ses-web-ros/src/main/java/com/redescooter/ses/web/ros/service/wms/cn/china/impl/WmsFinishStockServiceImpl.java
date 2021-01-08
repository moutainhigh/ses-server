package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.base.OpeColorMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartsRelationMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionScooterBomMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatGroupMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsPartsStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsFinishStockService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Autowired
    private OpeProductionScooterBomMapper opeProductionScooterBomMapper;

    @Autowired
    private OpeProductionPartsRelationMapper opeProductionPartsRelationMapper;

    @Autowired
    private OpeWmsPartsStockMapper opeWmsPartsStockMapper;

    @Autowired
    private OpeColorMapper opeColorMapper;

    @Autowired
    private OpeSpecificatGroupMapper opeSpecificatGroupMapper;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;



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
        if (result == null){
            result = new WmsStockCountResult();
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
        List<AbleProductionScooterResult> result = new ArrayList<>();

        // 查询整车bom表
        LambdaQueryWrapper<OpeProductionScooterBom> bomWrapper = new LambdaQueryWrapper<>();
        bomWrapper.eq(OpeProductionScooterBom::getDr, DelStatusEnum.VALID.getCode());
        bomWrapper.eq(OpeProductionScooterBom::getBomStatus, 1);
        List<OpeProductionScooterBom> bomList = opeProductionScooterBomMapper.selectList(bomWrapper);
        if (CollectionUtils.isEmpty(bomList)) {
            return result;
        }

        for (OpeProductionScooterBom bom : bomList) {
            AbleProductionScooterResult model = new AbleProductionScooterResult();
            Long productionId = bom.getId();
            Long groupId = bom.getGroupId();
            Long colorId = bom.getColorId();

            // 查询部件表
            LambdaQueryWrapper<OpeProductionPartsRelation> relationWrapper = new LambdaQueryWrapper<>();
            relationWrapper.eq(OpeProductionPartsRelation::getDr, DelStatusEnum.VALID.getCode());
            relationWrapper.eq(OpeProductionPartsRelation::getProductionId, productionId);
            List<OpeProductionPartsRelation> relationList = opeProductionPartsRelationMapper.selectList(relationWrapper);
            if (CollectionUtils.isEmpty(relationList)) {
                return result;
            }

            Integer[] numArray = new Integer[relationList.size()];
            for (int i = 0; i < relationList.size(); i++) {
                OpeProductionPartsRelation relation = relationList.get(i);
                Long partsId = relation.getPartsId();
                Integer partsQty = relation.getPartsQty();

                // 查询库存表中国仓库此部件的可用库存数量
                LambdaQueryWrapper<OpeWmsPartsStock> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeWmsPartsStock::getDr, DelStatusEnum.VALID.getCode());
                wrapper.eq(OpeWmsPartsStock::getStockType, 1);
                wrapper.eq(OpeWmsPartsStock::getPartsId, partsId);
                wrapper.orderByDesc(OpeWmsPartsStock::getCreatedTime);
                List<OpeWmsPartsStock> list = opeWmsPartsStockMapper.selectList(wrapper);
                if (CollectionUtils.isEmpty(list)) {
                    return result;
                }

                OpeWmsPartsStock stock = list.get(0);
                Integer ableStockQty = stock.getAbleStockQty();
                int count = ableStockQty / partsQty;
                numArray[i] = count;
            }
            int num = Collections.min(Arrays.asList(numArray));

            model.setNum(num);
            model.setColorName(getColorNameById(colorId));
            model.setGroupName(getGroupNameById(groupId));
            result.add(model);
        }
        return result;
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

    /**
     * 根据colorId获取colorName
     */
    public String getColorNameById(Long colorId) {
        if (null == colorId) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getMessage());
        }
        OpeColor color = opeColorMapper.selectById(colorId);
        if (null != color) {
            return color.getColorName();
        }
        throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
    }

    /**
     * 根据groupId获取groupName
     */
    public String getGroupNameById(Long groupId) {
        if (null == groupId) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        OpeSpecificatGroup group = opeSpecificatGroupMapper.selectById(groupId);
        if (null != group) {
            return group.getGroupName();
        }
        throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
    }


    /**
     * 出库单和入库单的数量统计，按国家区分（从库存点击出入库管理进入的）
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> outOrInOrderConunt(WmsStockTypeEnter enter) {
        Map<String, Integer> map = new HashMap<>();

        // 入库单
        QueryWrapper<OpeInWhouseOrder> in = new QueryWrapper<>();
        in.eq(OpeInWhouseOrder.COL_COUNTRY_TYPE,enter.getStockType());
        map.put("1",opeInWhouseOrderService.count(in));

        // 出库单
        QueryWrapper<OpeOutWhouseOrder> out = new QueryWrapper<>();
        out.eq(OpeOutWhouseOrder.COL_COUNTRY_TYPE,enter.getStockType());
        map.put("2", opeOutWhouseOrderService.count(out));
        return map;
    }
}
