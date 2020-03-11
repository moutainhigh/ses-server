package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysMenuMapper extends BaseMapper<OpeSysMenu> {
    int updateBatch(List<OpeSysMenu> list);

    int batchInsert(@Param("list") List<OpeSysMenu> list);

    int insertOrUpdate(OpeSysMenu record);

    int insertOrUpdateSelective(OpeSysMenu record);
}