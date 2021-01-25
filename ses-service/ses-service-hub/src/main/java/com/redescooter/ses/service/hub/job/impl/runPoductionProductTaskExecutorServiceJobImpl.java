package com.redescooter.ses.service.hub.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.hub.job.RunPoductionTaskExecutorServiceJob;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionCombinBom;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionScooterBom;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class runPoductionProductTaskExecutorServiceJobImpl implements RunPoductionTaskExecutorServiceJob {

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private IdAppService idAppService;

    /**
     * 组合批量发布
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public JobResult poductionCombinationTask(GeneralEnter enter) {
        List<OpeProductionCombinBom> opeProductionCombinBomList =
            opeProductionCombinBomService.list(new LambdaQueryWrapper<OpeProductionCombinBom>()
                .eq(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()));

        // 无需执行
        if (CollectionUtils.isEmpty(opeProductionCombinBomList)) {
            return JobResult.success();
        }

        List<OpeProductionCombinBom> expiredList = new ArrayList<>();
        List<OpeProductionCombinBom> activeList = new ArrayList<>();

        for (OpeProductionCombinBom item : opeProductionCombinBomList) {
            if(DateUtil.diffDays(item.getEffectiveDate(),new Date())<0){
                continue;
            }

            OpeProductionCombinBom avticeCombinBom =
                opeProductionCombinBomService.getOne(new LambdaQueryWrapper<OpeProductionCombinBom>()
                    .eq(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue()));
            if (avticeCombinBom != null) {
                avticeCombinBom.setBomStatus(ProductionBomStatusEnums.EXPIRED.getValue());
                avticeCombinBom.setUpdatedTime(new Date());
                expiredList.add(avticeCombinBom);
            }
            item.setBomStatus(ProductionBomStatusEnums.ACTIVE.getValue());
            item.setUpdatedTime(new Date());
            activeList.add(item);
        }

        if (CollectionUtils.isNotEmpty(expiredList)) {
            opeProductionCombinBomService.updateBatchById(expiredList);
        }
        if (CollectionUtils.isNotEmpty(expiredList)) {
            opeProductionCombinBomService.updateBatchById(activeList);

        }
        return JobResult.success();
    }

    /**
     * 车辆批量发布
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public JobResult productionScooterTask(GeneralEnter enter) {
        List<OpeProductionScooterBom> productionScooterBomList =
            opeProductionScooterBomService.list(new LambdaQueryWrapper<OpeProductionScooterBom>()
                .eq(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()));
        // 无需执行
        if (CollectionUtils.isEmpty(productionScooterBomList)) {
            return JobResult.success();
        }

        List<OpeProductionScooterBom> expiredList = new ArrayList<>();
        List<OpeProductionScooterBom> activeList = new ArrayList<>();

        for (OpeProductionScooterBom item : productionScooterBomList) {
            if(DateUtil.diffDays(item.getEffectiveDate(),new Date())<0){
                continue;
            }

            OpeProductionScooterBom avticeScooterBom =
                opeProductionScooterBomService.getOne(new LambdaQueryWrapper<OpeProductionScooterBom>()
                    .eq(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue()));
            if (avticeScooterBom != null) {
                avticeScooterBom.setBomStatus(ProductionBomStatusEnums.EXPIRED.getValue());
                avticeScooterBom.setUpdatedTime(new Date());
                expiredList.add(avticeScooterBom);
            }
            item.setBomStatus(ProductionBomStatusEnums.ACTIVE.getValue());
            item.setUpdatedTime(new Date());
            activeList.add(item);
        }

        if (CollectionUtils.isNotEmpty(expiredList)) {
            opeProductionScooterBomService.updateBatchById(expiredList);
        }
        if (CollectionUtils.isNotEmpty(expiredList)) {
            opeProductionScooterBomService.updateBatchById(activeList);

        }
        return null;
    }
}
