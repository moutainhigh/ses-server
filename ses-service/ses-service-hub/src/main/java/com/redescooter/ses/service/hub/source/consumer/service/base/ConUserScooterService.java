package com.redescooter.ses.service.hub.source.consumer.service.base;

import java.util.List;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserScooter;

public interface ConUserScooterService {


    int deleteByPrimaryKey(Long id);

    int insert(ConUserScooter record);

    int insertOrUpdate(ConUserScooter record);

    int insertOrUpdateSelective(ConUserScooter record);

    int insertSelective(ConUserScooter record);

    ConUserScooter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConUserScooter record);

    int updateByPrimaryKey(ConUserScooter record);

    int updateBatch(List<ConUserScooter> list);

    int batchInsert(List<ConUserScooter> list);

}

