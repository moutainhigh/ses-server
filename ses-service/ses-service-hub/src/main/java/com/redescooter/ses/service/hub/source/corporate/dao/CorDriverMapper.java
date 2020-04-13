package com.redescooter.ses.service.hub.source.corporate.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDriver;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@DS("corporate")
public interface CorDriverMapper extends BaseMapper<CorDriver> {
    int updateBatch(List<CorDriver> list);

    int batchInsert(@Param("list") List<CorDriver> list);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);
}