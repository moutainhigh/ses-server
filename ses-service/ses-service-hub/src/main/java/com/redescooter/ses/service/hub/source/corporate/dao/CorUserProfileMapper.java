package com.redescooter.ses.service.hub.source.corporate.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.hub.vo.QueryUserProfileByEmailEnter;
import com.redescooter.ses.api.hub.vo.QueryUserProfileByEmailResult;
import com.redescooter.ses.service.hub.source.corporate.dm.CorUserProfile;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@DS("corporate")
public interface CorUserProfileMapper extends BaseMapper<CorUserProfile> {
    int updateBatch(List<CorUserProfile> list);

    int batchInsert(@Param("list") List<CorUserProfile> list);

    int insertOrUpdate(CorUserProfile record);

    int insertOrUpdateSelective(CorUserProfile record);

    List<QueryUserProfileByEmailResult> QueryUserProfileByEmail(QueryUserProfileByEmailEnter enter);
}