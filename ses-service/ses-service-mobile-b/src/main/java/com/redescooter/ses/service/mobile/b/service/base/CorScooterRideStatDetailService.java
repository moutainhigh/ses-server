package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStatDetail;

import java.util.List;

public interface CorScooterRideStatDetailService extends IService<CorScooterRideStatDetail> {


    int updateBatch(List<CorScooterRideStatDetail> list);

    int batchInsert(List<CorScooterRideStatDetail> list);

    int insertOrUpdate(CorScooterRideStatDetail record);

    int insertOrUpdateSelective(CorScooterRideStatDetail record);

}

