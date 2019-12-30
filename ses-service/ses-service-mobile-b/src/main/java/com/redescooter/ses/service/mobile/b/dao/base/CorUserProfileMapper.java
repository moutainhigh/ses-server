package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorUserProfile;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorUserProfileMapper extends BaseMapper<CorUserProfile> {
    int updateBatch(List<CorUserProfile> list);

    int batchInsert(@Param("list") List<CorUserProfile> list);

    int insertOrUpdate(CorUserProfile record);

    int insertOrUpdateSelective(CorUserProfile record);
}