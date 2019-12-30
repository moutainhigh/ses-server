package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStatDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorScooterRideStatDetailMapper extends BaseMapper<CorScooterRideStatDetail> {
    int updateBatch(List<CorScooterRideStatDetail> list);

    int batchInsert(@Param("list") List<CorScooterRideStatDetail> list);

    int insertOrUpdate(CorScooterRideStatDetail record);

    int insertOrUpdateSelective(CorScooterRideStatDetail record);
}