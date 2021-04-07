package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsSec;

import java.util.List;

public interface OpePartsSecService extends IService<OpePartsSec>{


    int updateBatch(List<OpePartsSec> list);

    int batchInsert(List<OpePartsSec> list);

    int insertOrUpdate(OpePartsSec record);

    int insertOrUpdateSelective(OpePartsSec record);

}
