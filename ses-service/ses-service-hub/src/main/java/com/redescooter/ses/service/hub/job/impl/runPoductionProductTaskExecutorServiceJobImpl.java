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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@DubboService
public class runPoductionProductTaskExecutorServiceJobImpl implements RunPoductionTaskExecutorServiceJob {

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 组合批量发布
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public JobResult poductionCombinationTask(GeneralEnter enter) {

        // 所有待激活的组装件bom
        LambdaQueryWrapper<OpeProductionCombinBom> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
        List<OpeProductionCombinBom> list = opeProductionCombinBomService.list(qw);

        // 无需执行
        if (CollectionUtils.isEmpty(list)) {
            return JobResult.success();
        }

        // 失效集合
        List<OpeProductionCombinBom> expiredList = new ArrayList<>();
        // 激活集合
        List<OpeProductionCombinBom> activeList = new ArrayList<>();

        for (OpeProductionCombinBom item : list) {
            if (DateUtil.diffDays(item.getEffectiveDate(), new Date()) < 0) {
                continue;
            }

            LambdaQueryWrapper<OpeProductionCombinBom> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue());
            OpeProductionCombinBom activeCombinBom = opeProductionCombinBomService.getOne(lqw);
            if (null != activeCombinBom) {
                activeCombinBom.setBomStatus(ProductionBomStatusEnums.EXPIRED.getValue());
                activeCombinBom.setUpdatedTime(new Date());
                expiredList.add(activeCombinBom);
            }

            item.setBomStatus(ProductionBomStatusEnums.ACTIVE.getValue());
            item.setUpdatedTime(new Date());
            activeList.add(item);
        }

        if (CollectionUtils.isNotEmpty(expiredList)) {
            opeProductionCombinBomService.updateBatchById(expiredList);
        }
        if (CollectionUtils.isNotEmpty(activeList)) {
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
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public JobResult productionScooterTask(GeneralEnter enter) {

        // 所有待激活的车辆bom
        LambdaQueryWrapper<OpeProductionScooterBom> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
        List<OpeProductionScooterBom> list = opeProductionScooterBomService.list(qw);

        // 无需执行
        if (CollectionUtils.isEmpty(list)) {
            return JobResult.success();
        }

        // 失效集合
        List<OpeProductionScooterBom> expiredList = new ArrayList<>();
        // 激活集合
        List<OpeProductionScooterBom> activeList = new ArrayList<>();

        for (OpeProductionScooterBom item : list) {
            if (DateUtil.diffDays(item.getEffectiveDate(), new Date()) < 0) {
                continue;
            }

            LambdaQueryWrapper<OpeProductionScooterBom> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue());
            OpeProductionScooterBom activeScooterBom = opeProductionScooterBomService.getOne(lqw);
            if (null != activeScooterBom) {
                activeScooterBom.setBomStatus(ProductionBomStatusEnums.EXPIRED.getValue());
                activeScooterBom.setUpdatedTime(new Date());
                expiredList.add(activeScooterBom);
            }

            item.setBomStatus(ProductionBomStatusEnums.ACTIVE.getValue());
            item.setUpdatedTime(new Date());
            activeList.add(item);
        }

        if (CollectionUtils.isNotEmpty(expiredList)) {
            opeProductionScooterBomService.updateBatchById(expiredList);
        }
        if (CollectionUtils.isNotEmpty(activeList)) {
            opeProductionScooterBomService.updateBatchById(activeList);
        }
        return JobResult.success();
    }
}
