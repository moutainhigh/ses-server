package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplate;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePartQcTemplateService extends IService<OpePartQcTemplate> {


    int updateBatch(List<OpePartQcTemplate> list);

    int batchInsert(List<OpePartQcTemplate> list);

    int insertOrUpdate(OpePartQcTemplate record);

    int insertOrUpdateSelective(OpePartQcTemplate record);

}



