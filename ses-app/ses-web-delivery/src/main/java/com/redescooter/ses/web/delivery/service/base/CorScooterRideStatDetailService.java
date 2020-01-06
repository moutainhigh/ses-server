package com.redescooter.ses.web.delivery.service.base;

import java.util.List;
import com.redescooter.ses.web.delivery.dm.CorScooterRideStatDetail;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorScooterRideStatDetailService extends IService<CorScooterRideStatDetail>{


    int updateBatch(List<CorScooterRideStatDetail> list);

    int batchInsert(List<CorScooterRideStatDetail> list);

    int insertOrUpdate(CorScooterRideStatDetail record);

    int insertOrUpdateSelective(CorScooterRideStatDetail record);

}
