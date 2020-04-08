package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplateB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePartQcTemplateBService extends IService<OpePartQcTemplateB> {


    int updateBatch(List<OpePartQcTemplateB> list);

    int batchInsert(List<OpePartQcTemplateB> list);

    int insertOrUpdate(OpePartQcTemplateB record);

    int insertOrUpdateSelective(OpePartQcTemplateB record);

}





