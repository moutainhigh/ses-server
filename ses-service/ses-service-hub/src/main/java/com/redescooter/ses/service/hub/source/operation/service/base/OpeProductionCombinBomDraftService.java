package com.redescooter.ses.service.hub.source.operation.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionCombinBomDraft;

import java.util.List;

@DS("operation")
public interface OpeProductionCombinBomDraftService extends IService<OpeProductionCombinBomDraft> {

    int updateBatch(List<OpeProductionCombinBomDraft> list);

    int batchInsert(List<OpeProductionCombinBomDraft> list);

    int insertOrUpdate(OpeProductionCombinBomDraft record);

    int insertOrUpdateSelective(OpeProductionCombinBomDraft record);

}
