package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductQcTemplateService extends IService<OpeProductQcTemplate> {


    int updateBatch(List<OpeProductQcTemplate> list);

    int batchInsert(List<OpeProductQcTemplate> list);

    int insertOrUpdate(OpeProductQcTemplate record);

    int insertOrUpdateSelective(OpeProductQcTemplate record);

}

