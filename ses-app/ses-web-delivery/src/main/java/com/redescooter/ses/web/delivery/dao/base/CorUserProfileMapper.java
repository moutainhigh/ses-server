package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorUserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorUserProfileMapper extends BaseMapper<CorUserProfile> {
    int updateBatch(List<CorUserProfile> list);

    int batchInsert(@Param("list") List<CorUserProfile> list);

    int insertOrUpdate(CorUserProfile record);

    int insertOrUpdateSelective(CorUserProfile record);
}