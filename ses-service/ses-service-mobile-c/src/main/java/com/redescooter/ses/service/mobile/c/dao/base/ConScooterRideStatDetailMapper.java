package com.redescooter.ses.service.mobile.c.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStatDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConScooterRideStatDetailMapper extends BaseMapper<ConScooterRideStatDetail> {
    int updateBatch(List<ConScooterRideStatDetail> list);

    int batchInsert(@Param("list") List<ConScooterRideStatDetail> list);

    int insertOrUpdate(ConScooterRideStatDetail record);

    int insertOrUpdateSelective(ConScooterRideStatDetail record);
}