package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysUserRoleMapper extends BaseMapper<OpeSysUserRole> {
    int updateBatch(List<OpeSysUserRole> list);

    int batchInsert(@Param("list") List<OpeSysUserRole> list);

    int insertOrUpdate(OpeSysUserRole record);

    int insertOrUpdateSelective(OpeSysUserRole record);
}