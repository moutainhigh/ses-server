package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysDeptRelationMapper extends BaseMapper<OpeSysDeptRelation> {
    int batchInsert(@Param("list") List<OpeSysDeptRelation> list);

    int insertOrUpdate(OpeSysDeptRelation record);

    int insertOrUpdateSelective(OpeSysDeptRelation record);
}