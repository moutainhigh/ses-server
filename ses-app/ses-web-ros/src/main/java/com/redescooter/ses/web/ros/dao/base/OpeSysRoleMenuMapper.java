package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysRoleMenuMapper extends BaseMapper<OpeSysRoleMenu> {
    int updateBatch(List<OpeSysRoleMenu> list);

    int batchInsert(@Param("list") List<OpeSysRoleMenu> list);

    int insertOrUpdate(OpeSysRoleMenu record);

    int insertOrUpdateSelective(OpeSysRoleMenu record);
}