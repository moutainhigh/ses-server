package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysDeptRelationMapper extends BaseMapper<OpeSysDeptRelation> {
    int batchInsert(@Param("list") List<OpeSysDeptRelation> list);

    int insertOrUpdate(OpeSysDeptRelation record);

    int insertOrUpdateSelective(OpeSysDeptRelation record);

    int updateBatch(List<OpeSysDeptRelation> list);
}