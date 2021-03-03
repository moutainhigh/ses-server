package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysUserMapper extends BaseMapper<OpeSysUser> {
    int updateBatch(List<OpeSysUser> list);

    int batchInsert(@Param("list") List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);

    List<String> findPerms(@Param("userId") Long userId);

    List<String> findAllPerms();
}