package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplateB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePartDraftQcTemplateBService extends IService<OpePartDraftQcTemplateB> {


    int updateBatch(List<OpePartDraftQcTemplateB> list);

    int batchInsert(List<OpePartDraftQcTemplateB> list);

    int insertOrUpdate(OpePartDraftQcTemplateB record);

    int insertOrUpdateSelective(OpePartDraftQcTemplateB record);

}




