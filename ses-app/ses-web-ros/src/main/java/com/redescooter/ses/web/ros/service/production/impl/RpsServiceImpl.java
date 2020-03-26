package com.redescooter.ses.web.ros.service.production.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpePurchas;
import com.redescooter.ses.web.ros.dm.OpePurchasB;
import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePurchasBQcService;
import com.redescooter.ses.web.ros.service.base.OpePurchasBService;
import com.redescooter.ses.web.ros.service.base.OpePurchasService;
import com.redescooter.ses.web.ros.service.production.RpsServvice;
import com.redescooter.ses.web.ros.vo.production.ScanBarCodeEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:RpsServiceImpl
 * @description: RpsServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/25 12:11
 */
@Service
public class RpsServiceImpl implements RpsServvice {

    @Autowired
    private OpePurchasService opePurchasService;

    @Autowired
    private OpePurchasBService opePurchasBService;

    @Autowired
    private OpePurchasBQcService opePurchasBQcService;

    @Reference
    private IdAppService idAppService;

    /**
     * 模拟RpS进行质检
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional
    public GeneralResult scanBarCode(ScanBarCodeEnter enter) {
        OpePurchas opePurchas = opePurchasService.getById(enter.getPurchasingId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //查询采购条目
        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, enter.getPurchasingId());
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);

        List<OpePurchasBQc> opePurchasBServiceList = Lists.newArrayList();

        //构建qc质检数据
        purchasBList.forEach(item -> {
            opePurchasBServiceList.add(
                    OpePurchasBQc.builder()
                            .id(idAppService.getId(SequenceName.OPE_PURCHAS_B_QC))
                            .dr(0)
                            .tenantId(0L)
                            .userId(enter.getUserId())
                            .purchasBId(enter.getPurchasingId())
                            .purchasBId(item.getId())
                            .partsId(enter.getPartId())
                            .qualityInspectorId(enter.getUserId())
                            .batchNo("REDE" + RandomUtil.randomLong(10000, 99999))
                            .status(CollectionUtils.isNotEmpty(enter.getFailParts()) == true ? QcStatusEnums.PASS.getValue() : QcStatusEnums.QUALITY_INSPECTION.getValue())
                            .totalQualityInspected(item.getTotalCount())
                            .passCount(item.getTotalCount() / 2)
                            .failCount(item.getTotalCount() / 2)
                            .qualityInspectionTime(new Date())
                            .revision(0)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedBy(enter.getUserId())
                            .updatedTime(new Date())
                            .build()
            );
        });
        opePurchasBQcService.saveBatch(opePurchasBServiceList);
        return new GeneralResult(enter.getRequestId());
    }
}
