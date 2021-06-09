package com.redescooter.ses.web.ros.service.delete.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.delete.DeleteMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRelation;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRsn;
import com.redescooter.ses.web.ros.dm.OpeCodebaseVin;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRelationService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseVinService;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.service.delete.DeleteService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:46
 */
@Service
@Slf4j
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeCodebaseRelationService opeCodebaseRelationService;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private DeleteMapper deleteMapper;

    @DubboReference
    private ScooterService scooterService;

    /**
     * 删除车辆bom
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteScooterBom(StringEnter enter) {
        LambdaQueryWrapper<OpeProductionScooterBom> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeProductionScooterBom::getDr, Constant.DR_FALSE);
        qw.in(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
        qw.eq(OpeProductionScooterBom::getBomNo, enter.getKeyword());
        List<OpeProductionScooterBom> list = opeProductionScooterBomService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeProductionScooterBom bom : list) {
                Long id = bom.getId();
                deleteMapper.deleteScooterBom(id);
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除组装件bom
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteCombinBom(StringEnter enter) {
        LambdaQueryWrapper<OpeProductionCombinBom> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeProductionCombinBom::getDr, Constant.DR_FALSE);
        qw.in(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
        qw.eq(OpeProductionCombinBom::getBomNo, enter.getKeyword());
        List<OpeProductionCombinBom> list = opeProductionCombinBomService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeProductionCombinBom bom : list) {
                Long id = bom.getId();
                deleteMapper.deleteCombinBom(id);
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteScooter(StringEnter enter) {
        String tabletSn = enter.getKeyword();

        // 删除scooter库
        String rsn = scooterService.deleteScooter(tabletSn);

        // 修改ope_wms_stock_serial_number
        LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        qw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        qw.eq(OpeWmsStockSerialNumber::getStockStatus, 2);
        List<OpeWmsStockSerialNumber> list = opeWmsStockSerialNumberService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeWmsStockSerialNumber item : list) {
                item.setStockStatus(1);
                opeWmsStockSerialNumberService.updateById(item);
            }
        }

        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getRsn, rsn);
        lqw.eq(OpeCarDistribute::getTabletSn, tabletSn);
        lqw.last("limit 1");
        OpeCarDistribute distribute = opeCarDistributeMapper.selectOne(lqw);
        if (null != distribute) {
            String vinCode = distribute.getVinCode();
            if (StringUtils.isNotBlank(vinCode)) {

                // 修改ope_codebase_rsn
                LambdaQueryWrapper<OpeCodebaseRsn> rsnWrapper = new LambdaQueryWrapper<>();
                rsnWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
                rsnWrapper.eq(OpeCodebaseRsn::getRsn, rsn);
                rsnWrapper.eq(OpeCodebaseRsn::getStatus, 2);
                rsnWrapper.last("limit 1");
                OpeCodebaseRsn rsnModel = opeCodebaseRsnService.getOne(rsnWrapper);
                if (null != rsnModel) {
                    rsnModel.setStatus(1);
                    opeCodebaseRsnService.updateById(rsnModel);
                }

                // 修改ope_codebase_vin
                LambdaQueryWrapper<OpeCodebaseVin> vinWrapper = new LambdaQueryWrapper<>();
                vinWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
                vinWrapper.eq(OpeCodebaseVin::getVin, vinCode);
                vinWrapper.eq(OpeCodebaseVin::getStatus, 2);
                vinWrapper.last("limit 1");
                OpeCodebaseVin vinModel = opeCodebaseVinService.getOne(vinWrapper);
                if (null != vinModel) {
                    vinModel.setStatus(1);
                    opeCodebaseVinService.updateById(vinModel);
                }

                // 删除ope_codebase_relation
                LambdaQueryWrapper<OpeCodebaseRelation> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeCodebaseRelation::getRsn, rsn);
                wrapper.eq(OpeCodebaseRelation::getVin, vinCode);
                wrapper.last("limit 1");
                OpeCodebaseRelation relation = opeCodebaseRelationService.getOne(wrapper);
                if (null != relation) {
                    deleteMapper.deleteCodebaseRelation(relation.getId());
                }
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

}
