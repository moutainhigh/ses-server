package com.redescooter.ses.service.mobile.c.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStatDetail;

import java.util.List;

public interface ConScooterRideStatDetailService extends IService<ConScooterRideStatDetail> {


    int updateBatch(List<ConScooterRideStatDetail> list);

    int batchInsert(List<ConScooterRideStatDetail> list);

    int insertOrUpdate(ConScooterRideStatDetail record);

    int insertOrUpdateSelective(ConScooterRideStatDetail record);

}

