package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorDriverMapper extends BaseMapper<CorDriver> {
    int updateBatch(List<CorDriver> list);

    int batchInsert(@Param("list") List<CorDriver> list);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);
}