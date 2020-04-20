package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePartDraftQcTemplateService extends IService<OpePartDraftQcTemplate> {


    int updateBatch(List<OpePartDraftQcTemplate> list);

    int batchInsert(List<OpePartDraftQcTemplate> list);

    int insertOrUpdate(OpePartDraftQcTemplate record);

    int insertOrUpdateSelective(OpePartDraftQcTemplate record);

}


