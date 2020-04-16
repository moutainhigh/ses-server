package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysUserProfile;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysUserProfileMapper extends BaseMapper<OpeSysUserProfile> {
    int updateBatch(List<OpeSysUserProfile> list);

    int batchInsert(@Param("list") List<OpeSysUserProfile> list);

    int insertOrUpdate(OpeSysUserProfile record);

    int insertOrUpdateSelective(OpeSysUserProfile record);
}