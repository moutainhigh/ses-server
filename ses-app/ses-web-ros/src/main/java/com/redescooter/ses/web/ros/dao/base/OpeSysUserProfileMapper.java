package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysUserProfileMapper extends BaseMapper<OpeSysUserProfile> {
    int updateBatch(List<OpeSysUserProfile> list);

    int batchInsert(@Param("list") List<OpeSysUserProfile> list);

    int insertOrUpdate(OpeSysUserProfile record);

    int insertOrUpdateSelective(OpeSysUserProfile record);
}