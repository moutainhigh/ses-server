package com.redescooter.ses.service.hub.source.consumer.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserProfile;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@DS("consumer")
public interface ConUserProfileMapper extends BaseMapper<ConUserProfile> {
    int updateBatch(List<ConUserProfile> list);

    int batchInsert(@Param("list") List<ConUserProfile> list);

    int insertOrUpdate(ConUserProfile record);

    int insertOrUpdateSelective(ConUserProfile record);
}