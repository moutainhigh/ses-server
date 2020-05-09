package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplate;

import java.util.List;

public interface OpePartDraftQcTemplateService extends IService<OpePartDraftQcTemplate> {


    int updateBatch(List<OpePartDraftQcTemplate> list);

    int batchInsert(List<OpePartDraftQcTemplate> list);

    int insertOrUpdate(OpePartDraftQcTemplate record);

    int insertOrUpdateSelective(OpePartDraftQcTemplate record);

}



