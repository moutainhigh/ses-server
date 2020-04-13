package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStatDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorDriverRideStatDetailMapper extends BaseMapper<CorDriverRideStatDetail> {
    int updateBatch(List<CorDriverRideStatDetail> list);

    int batchInsert(@Param("list") List<CorDriverRideStatDetail> list);

    int insertOrUpdate(CorDriverRideStatDetail record);

    int insertOrUpdateSelective(CorDriverRideStatDetail record);
}