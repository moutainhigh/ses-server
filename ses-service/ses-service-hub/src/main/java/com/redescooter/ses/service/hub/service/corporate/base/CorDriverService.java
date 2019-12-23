package com.redescooter.ses.service.hub.service.corporate.base;

import java.util.List;
import com.redescooter.ses.service.hub.dm.corporate.base.CorDriver;
public interface CorDriverService{


    int deleteByPrimaryKey(Long id);

    int insert(CorDriver record);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);

    int insertSelective(CorDriver record);

    CorDriver selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CorDriver record);

    int updateByPrimaryKey(CorDriver record);

    int updateBatch(List<CorDriver> list);

    int batchInsert(List<CorDriver> list);

}
