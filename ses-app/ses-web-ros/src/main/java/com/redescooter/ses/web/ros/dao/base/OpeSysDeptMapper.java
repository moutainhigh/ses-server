package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysDeptMapper extends BaseMapper<OpeSysDept> {
    int updateBatch(List<OpeSysDept> list);

    int updateBatchSelective(List<OpeSysDept> list);

    int batchInsert(@Param("list") List<OpeSysDept> list);

    int insertOrUpdate(OpeSysDept record);

    int insertOrUpdateSelective(OpeSysDept record);
}