package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStat;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorDriverRideStatMapper extends BaseMapper<CorDriverRideStat> {
    int updateBatch(List<CorDriverRideStat> list);

    int batchInsert(@Param("list") List<CorDriverRideStat> list);

    int insertOrUpdate(CorDriverRideStat record);

    int insertOrUpdateSelective(CorDriverRideStat record);
}