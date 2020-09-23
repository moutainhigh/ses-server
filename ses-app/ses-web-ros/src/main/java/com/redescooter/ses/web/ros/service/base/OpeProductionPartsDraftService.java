package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;

public interface OpeProductionPartsDraftService {


    int deleteByPrimaryKey(Long id);

    int insert(OpeProductionPartsDraft record);

    int insertOrUpdate(OpeProductionPartsDraft record);

    int insertOrUpdateSelective(OpeProductionPartsDraft record);

    int insertSelective(OpeProductionPartsDraft record);

    OpeProductionPartsDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductionPartsDraft record);

    int updateByPrimaryKey(OpeProductionPartsDraft record);

    int updateBatch(List<OpeProductionPartsDraft> list);

    int updateBatchSelective(List<OpeProductionPartsDraft> list);

    int batchInsert(List<OpeProductionPartsDraft> list);

}

