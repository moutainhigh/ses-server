package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysPosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSysPositionMapper extends BaseMapper<OpeSysPosition> {
    int updateBatch(List<OpeSysPosition> list);

    int batchInsert(@Param("list") List<OpeSysPosition> list);

    int insertOrUpdate(OpeSysPosition record);

    int insertOrUpdateSelective(OpeSysPosition record);
}