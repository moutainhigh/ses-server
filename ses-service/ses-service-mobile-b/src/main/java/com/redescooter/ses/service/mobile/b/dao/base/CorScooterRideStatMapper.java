package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStat;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorScooterRideStatMapper extends BaseMapper<CorScooterRideStat> {
    int updateBatch(List<CorScooterRideStat> list);

    int batchInsert(@Param("list") List<CorScooterRideStat> list);

    int insertOrUpdate(CorScooterRideStat record);

    int insertOrUpdateSelective(CorScooterRideStat record);
}