package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplateB;

import java.util.List;

public interface OpeProductQcTemplateBService extends IService<OpeProductQcTemplateB> {


    int updateBatch(List<OpeProductQcTemplateB> list);

    int batchInsert(List<OpeProductQcTemplateB> list);

    int insertOrUpdate(OpeProductQcTemplateB record);

    int insertOrUpdateSelective(OpeProductQcTemplateB record);

}



