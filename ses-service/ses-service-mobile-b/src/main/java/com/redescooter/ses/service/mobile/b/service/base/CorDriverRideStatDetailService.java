package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStatDetail;

import java.util.List;

public interface CorDriverRideStatDetailService extends IService<CorDriverRideStatDetail> {


    int updateBatch(List<CorDriverRideStatDetail> list);

    int batchInsert(List<CorDriverRideStatDetail> list);

    int insertOrUpdate(CorDriverRideStatDetail record);

    int insertOrUpdateSelective(CorDriverRideStatDetail record);

}

