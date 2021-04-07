package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBomDraft;

import java.util.List;

public interface OpeProductionCombinBomDraftService extends IService<OpeProductionCombinBomDraft> {

    int updateBatch(List<OpeProductionCombinBomDraft> list);

    int batchInsert(List<OpeProductionCombinBomDraft> list);

    int insertOrUpdate(OpeProductionCombinBomDraft record);

    int insertOrUpdateSelective(OpeProductionCombinBomDraft record);

}


