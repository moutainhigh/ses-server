package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysRoleMapper extends BaseMapper<OpeSysRole> {
    int updateBatch(List<OpeSysRole> list);

    int batchInsert(@Param("list") List<OpeSysRole> list);

    int insertOrUpdate(OpeSysRole record);

    int insertOrUpdateSelective(OpeSysRole record);
}