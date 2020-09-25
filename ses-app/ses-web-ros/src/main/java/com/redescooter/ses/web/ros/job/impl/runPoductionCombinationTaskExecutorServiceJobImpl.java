package com.redescooter.ses.web.ros.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.job.runPoductionCombinationTaskExecutorServiceJob;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import spark.utils.CollectionUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class runPoductionCombinationTaskExecutorServiceJobImpl
    implements runPoductionCombinationTaskExecutorServiceJob {

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

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
        for (OpeProductionCombinBom item : opeProductionCombinBomList) {
            if (item.getEffectiveDate().before(new Date())) {
                continue;
            }

            OpeProductionCombinBom avticeCombinBom =
                opeProductionCombinBomService.getOne(new LambdaQueryWrapper<OpeProductionCombinBom>()
                    .eq(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue()));
            if (avticeCombinBom != null) {
                avticeCombinBom.setBomStatus(ProductionBomStatusEnums.EXPIRED.getValue());
                avticeCombinBom.setUpdatedBy(item.getId());
                avticeCombinBom.setUpdatedTime(new Date());
                opeProductionCombinBomService.updateById(avticeCombinBom);
            }
            item.setBomStatus(ProductionBomStatusEnums.ACTIVE.getValue());
            item.setUpdatedBy(0L);
            item.setUpdatedTime(new Date());
            opeProductionCombinBomService.updateById(item);
        }
        return null;
    }

}
