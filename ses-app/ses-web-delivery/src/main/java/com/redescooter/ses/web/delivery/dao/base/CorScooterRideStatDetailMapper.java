package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorScooterRideStatDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorScooterRideStatDetailMapper extends BaseMapper<CorScooterRideStatDetail> {
    int updateBatch(List<CorScooterRideStatDetail> list);

    int batchInsert(@Param("list") List<CorScooterRideStatDetail> list);

    int insertOrUpdate(CorScooterRideStatDetail record);

    int insertOrUpdateSelective(CorScooterRideStatDetail record);
}