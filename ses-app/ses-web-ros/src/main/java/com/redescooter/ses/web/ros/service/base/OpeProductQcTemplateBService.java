package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplateB;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeProductQcTemplateBService extends IService<OpeProductQcTemplateB> {


    int updateBatch(List<OpeProductQcTemplateB> list);

    int batchInsert(List<OpeProductQcTemplateB> list);

    int insertOrUpdate(OpeProductQcTemplateB record);

    int insertOrUpdateSelective(OpeProductQcTemplateB record);

}


