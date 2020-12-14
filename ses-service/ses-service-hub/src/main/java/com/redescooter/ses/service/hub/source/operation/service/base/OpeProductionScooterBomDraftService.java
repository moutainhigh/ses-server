package com.redescooter.ses.service.hub.source.operation.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionScooterBomDraft;

import java.util.List;

@DS("operation")
public interface OpeProductionScooterBomDraftService extends IService<OpeProductionScooterBomDraft> {

    int updateBatch(List<OpeProductionScooterBomDraft> list);

    int batchInsert(List<OpeProductionScooterBomDraft> list);

    int insertOrUpdate(OpeProductionScooterBomDraft record);

    int insertOrUpdateSelective(OpeProductionScooterBomDraft record);

}
