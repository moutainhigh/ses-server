package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import java.util.List;
public interface OpeProductionPartsService{


    int deleteByPrimaryKey(Long id);

    int insert(OpeProductionParts record);

    int insertOrUpdate(OpeProductionParts record);

    int insertOrUpdateSelective(OpeProductionParts record);

    int insertSelective(OpeProductionParts record);

    OpeProductionParts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductionParts record);

    int updateByPrimaryKey(OpeProductionParts record);

    int updateBatch(List<OpeProductionParts> list);

    int batchInsert(List<OpeProductionParts> list);

}
