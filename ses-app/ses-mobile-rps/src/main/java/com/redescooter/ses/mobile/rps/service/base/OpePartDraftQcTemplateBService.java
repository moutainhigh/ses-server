package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePartDraftQcTemplateB;

import java.util.List;

public interface OpePartDraftQcTemplateBService extends IService<OpePartDraftQcTemplateB>{


    int updateBatch(List<OpePartDraftQcTemplateB> list);

    int batchInsert(List<OpePartDraftQcTemplateB> list);

    int insertOrUpdate(OpePartDraftQcTemplateB record);

    int insertOrUpdateSelective(OpePartDraftQcTemplateB record);

}
