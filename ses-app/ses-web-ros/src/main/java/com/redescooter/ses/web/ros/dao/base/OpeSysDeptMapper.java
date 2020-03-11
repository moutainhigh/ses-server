package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysDeptMapper extends BaseMapper<OpeSysDept> {
    int updateBatch(List<OpeSysDept> list);

    int batchInsert(@Param("list") List<OpeSysDept> list);

    int insertOrUpdate(OpeSysDept record);

    int insertOrUpdateSelective(OpeSysDept record);
}