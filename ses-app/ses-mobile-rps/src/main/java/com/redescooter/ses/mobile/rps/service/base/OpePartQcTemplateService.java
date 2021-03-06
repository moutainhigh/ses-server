package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplate;

import java.util.List;

public interface OpePartQcTemplateService extends IService<OpePartQcTemplate> {


    int updateBatch(List<OpePartQcTemplate> list);

    int batchInsert(List<OpePartQcTemplate> list);

    int insertOrUpdate(OpePartQcTemplate record);

    int insertOrUpdateSelective(OpePartQcTemplate record);

}

