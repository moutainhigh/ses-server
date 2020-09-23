package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBomDraft;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductionScooterBomDraftService extends IService<OpeProductionScooterBomDraft> {

    int updateBatch(List<OpeProductionScooterBomDraft> list);

    int batchInsert(List<OpeProductionScooterBomDraft> list);

    int insertOrUpdate(OpeProductionScooterBomDraft record);

    int insertOrUpdateSelective(OpeProductionScooterBomDraft record);

}

