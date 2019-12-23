package com.redescooter.ses.service.mobile.c.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConUserProfileMapper extends BaseMapper<ConUserProfile> {
    int updateBatch(List<ConUserProfile> list);

    int batchInsert(@Param("list") List<ConUserProfile> list);

    int insertOrUpdate(ConUserProfile record);

    int insertOrUpdateSelective(ConUserProfile record);
}