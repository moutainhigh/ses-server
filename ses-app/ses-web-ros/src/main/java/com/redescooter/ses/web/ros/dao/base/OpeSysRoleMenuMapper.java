package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysRoleMenuMapper extends BaseMapper<OpeSysRoleMenu> {
    int batchInsert(@Param("list") List<OpeSysRoleMenu> list);

    int insertOrUpdate(OpeSysRoleMenu record);

    int insertOrUpdateSelective(OpeSysRoleMenu record);
}