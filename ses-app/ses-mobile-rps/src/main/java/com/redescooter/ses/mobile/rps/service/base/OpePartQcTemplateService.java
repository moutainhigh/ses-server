package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePartQcTemplateService extends IService<OpePartQcTemplate> {


    int updateBatch(List<OpePartQcTemplate> list);

    int batchInsert(List<OpePartQcTemplate> list);

    int insertOrUpdate(OpePartQcTemplate record);

    int insertOrUpdateSelective(OpePartQcTemplate record);

}



