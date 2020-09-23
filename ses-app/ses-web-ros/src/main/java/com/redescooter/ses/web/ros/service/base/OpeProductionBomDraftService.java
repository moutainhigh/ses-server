package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionBomDraft;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductionBomDraftService extends IService<OpeProductionBomDraft> {

    int updateBatch(List<OpeProductionBomDraft> list);

    int batchInsert(List<OpeProductionBomDraft> list);

    int insertOrUpdate(OpeProductionBomDraft record);

    int insertOrUpdateSelective(OpeProductionBomDraft record);

}
