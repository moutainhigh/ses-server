package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplateB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePartQcTemplateBService extends IService<OpePartQcTemplateB> {


    int updateBatch(List<OpePartQcTemplateB> list);

    int batchInsert(List<OpePartQcTemplateB> list);

    int insertOrUpdate(OpePartQcTemplateB record);

    int insertOrUpdateSelective(OpePartQcTemplateB record);

}


