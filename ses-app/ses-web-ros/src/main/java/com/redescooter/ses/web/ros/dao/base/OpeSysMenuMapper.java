package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSysMenuMapper extends BaseMapper<OpeSysMenu> {
    int updateBatch(List<OpeSysMenu> list);

    int batchInsert(@Param("list") List<OpeSysMenu> list);

    int insertOrUpdate(OpeSysMenu record);

    int insertOrUpdateSelective(OpeSysMenu record);
}