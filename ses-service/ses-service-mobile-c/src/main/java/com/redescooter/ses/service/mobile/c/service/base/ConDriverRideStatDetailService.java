package com.redescooter.ses.service.mobile.c.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStatDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ConDriverRideStatDetailService extends IService<ConDriverRideStatDetail> {


    int updateBatch(List<ConDriverRideStatDetail> list);

    int batchInsert(List<ConDriverRideStatDetail> list);

    int insertOrUpdate(ConDriverRideStatDetail record);

    int insertOrUpdateSelective(ConDriverRideStatDetail record);

}

