package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplateB;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePartQcTemplateBService extends IService<OpePartQcTemplateB> {


    int updateBatch(List<OpePartQcTemplateB> list);

    int batchInsert(List<OpePartQcTemplateB> list);

    int insertOrUpdate(OpePartQcTemplateB record);

    int insertOrUpdateSelective(OpePartQcTemplateB record);

}

