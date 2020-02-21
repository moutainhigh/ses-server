package com.redescooter.ses.service.mobile.c.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStatDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConDriverRideStatDetailMapper extends BaseMapper<ConDriverRideStatDetail> {
    int updateBatch(List<ConDriverRideStatDetail> list);

    int batchInsert(@Param("list") List<ConDriverRideStatDetail> list);

    int insertOrUpdate(ConDriverRideStatDetail record);

    int insertOrUpdateSelective(ConDriverRideStatDetail record);
}