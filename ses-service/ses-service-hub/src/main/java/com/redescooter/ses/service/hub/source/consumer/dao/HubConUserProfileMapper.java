package com.redescooter.ses.service.hub.source.consumer.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("consumer")
public interface HubConUserProfileMapper extends BaseMapper<HubConUserProfile> {
    int updateBatch(List<HubConUserProfile> list);

    int batchInsert(@Param("list") List<HubConUserProfile> list);

    int insertOrUpdate(HubConUserProfile record);

    int insertOrUpdateSelective(HubConUserProfile record);
}