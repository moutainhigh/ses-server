package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpePartQcTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePartQcTemplateService extends IService<OpePartQcTemplate> {


    int updateBatch(List<OpePartQcTemplate> list);

    int batchInsert(List<OpePartQcTemplate> list);

    int insertOrUpdate(OpePartQcTemplate record);

    int insertOrUpdateSelective(OpePartQcTemplate record);

}

