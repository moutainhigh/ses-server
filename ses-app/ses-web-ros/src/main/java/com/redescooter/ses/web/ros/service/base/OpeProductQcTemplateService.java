package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplate;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeProductQcTemplateService extends IService<OpeProductQcTemplate> {


    int updateBatch(List<OpeProductQcTemplate> list);

    int batchInsert(List<OpeProductQcTemplate> list);

    int insertOrUpdate(OpeProductQcTemplate record);

    int insertOrUpdateSelective(OpeProductQcTemplate record);

}

