package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplateB;

import java.util.List;

public interface OpePartDraftQcTemplateBService extends IService<OpePartDraftQcTemplateB> {


    int updateBatch(List<OpePartDraftQcTemplateB> list);

    int batchInsert(List<OpePartDraftQcTemplateB> list);

    int insertOrUpdate(OpePartDraftQcTemplateB record);

    int insertOrUpdateSelective(OpePartDraftQcTemplateB record);

}





