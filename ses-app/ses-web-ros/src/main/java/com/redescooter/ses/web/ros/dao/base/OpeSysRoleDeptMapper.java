package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysRoleDeptMapper extends BaseMapper<OpeSysRoleDept> {
    int updateBatch(List<OpeSysRoleDept> list);

    int batchInsert(@Param("list") List<OpeSysRoleDept> list);

    int insertOrUpdate(OpeSysRoleDept record);

    int insertOrUpdateSelective(OpeSysRoleDept record);
}