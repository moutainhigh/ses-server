package com.redescooter.ses.service.mobile.c.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConScooterRideStatMapper extends BaseMapper<ConScooterRideStat> {
    int updateBatch(List<ConScooterRideStat> list);

    int batchInsert(@Param("list") List<ConScooterRideStat> list);

    int insertOrUpdate(ConScooterRideStat record);

    int insertOrUpdateSelective(ConScooterRideStat record);
}