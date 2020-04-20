package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeProductQcTemplateService extends IService<OpeProductQcTemplate>{


    int updateBatch(List<OpeProductQcTemplate> list);

    int batchInsert(List<OpeProductQcTemplate> list);

    int insertOrUpdate(OpeProductQcTemplate record);

    int insertOrUpdateSelective(OpeProductQcTemplate record);

}
