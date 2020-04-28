package com.redescooter.ses.mobile.rps.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.dao.RpsHeadServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocate;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import com.redescooter.ses.mobile.rps.service.RpsHeadService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.vo.materialqc.RpsHeadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName:RpsHeadServiceImpl
 * @description: RpsHeadServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:46
 */
@Service
public class RpsHeadServiceImpl implements RpsHeadService {

    @Autowired
    private RpsHeadServiceMapper rpsHeadServiceMapper;

    @Autowired
    private OpeAllocateService opeAllocateService;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    /**
     * rps 首页数据
     *
     * @param enter
     * @return
     */
    @Override
    public RpsHeadResult rpsHead(GeneralEnter enter) {
        //待备料 统计
        int prepareCount = opeAllocateService.count(new LambdaQueryWrapper<OpeAllocate>()
                .eq(OpeAllocate::getStatus, AllocateOrderStatusEnums.PENDING.getValue())
                .ne(OpeAllocate::getPreparationWaitTotal, 0))
                +
                opeAssemblyOrderService.count(new LambdaQueryWrapper<OpeAssemblyOrder>()
                        .eq(OpeAssemblyOrder::getStatus, AssemblyStatusEnums.PENDING.getValue())
                        .ne(OpeAssemblyOrder::getLaveWaitQcTotal, 0));
        //生产入库统计
        int productionInWh = opeAllocateService.count(new LambdaQueryWrapper<OpeAllocate>()
                .eq(OpeAllocate::getStatus, AllocateOrderStatusEnums.ALLOCATE.getValue())
                .ne(OpeAllocate::getPendingStorageTotal, 0))
                +
                opeAssemblyOrderService.count(new LambdaQueryWrapper<OpeAssemblyOrder>()
                        .eq(OpeAssemblyOrder::getStatus, AssemblyStatusEnums.QC_PASSED.getValue())
                        .ne(OpeAssemblyOrder::getInWaitWhTotal, 0));
        return RpsHeadResult.builder()
                //来料质检
                .materialQcTotal(rpsHeadServiceMapper.rpsHeadMaterialsQc(enter))
                //采购入库
                .purchasInWhTotal(rpsHeadServiceMapper.rpsHeadPurchasInWh(enter))
                //待备料（调拨单、组装单）
                .prepareMaterialTotal(prepareCount)
                //整车组装
                .assemblyTotal(rpsHeadServiceMapper.rpsHeadAssembly(enter))
                //整车质检
                .scooterQcTotal(rpsHeadServiceMapper.rpsHeadProductQc(enter))
                //生产入库(组装单、调拨单)
                .productionInWhTotal(productionInWh)
                .build();
    }
}
