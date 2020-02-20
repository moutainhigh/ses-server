package com.redescooter.ses.service.mobile.c.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStatDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConDriverRideStatDetailMapper extends BaseMapper<ConDriverRideStatDetail> {
    int updateBatch(List<ConDriverRideStatDetail> list);

    int batchInsert(@Param("list") List<ConDriverRideStatDetail> list);

    int insertOrUpdate(ConDriverRideStatDetail record);

    int insertOrUpdateSelective(ConDriverRideStatDetail record);
}