package com.redescooter.ses.service.hub.source.consumer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserScooter;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConUserScooterMapper extends BaseMapper<HubConUserScooter> {
    int updateBatch(List<HubConUserScooter> list);

    int batchInsert(@Param("list") List<HubConUserScooter> list);

    int insertOrUpdate(HubConUserScooter record);

    int insertOrUpdateSelective(HubConUserScooter record);
}