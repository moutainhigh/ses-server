package com.redescooter.ses.service.mobile.c.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStat;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConDriverRideStatMapper extends BaseMapper<ConDriverRideStat> {
    int updateBatch(List<ConDriverRideStat> list);

    int batchInsert(@Param("list") List<ConDriverRideStat> list);

    int insertOrUpdate(ConDriverRideStat record);

    int insertOrUpdateSelective(ConDriverRideStat record);
}