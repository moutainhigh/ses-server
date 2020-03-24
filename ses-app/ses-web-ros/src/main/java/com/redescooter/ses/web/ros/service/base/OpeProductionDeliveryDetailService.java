package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryDetail;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionDeliveryDetailService extends IService<OpeProductionDeliveryDetail> {


    int updateBatch(List<OpeProductionDeliveryDetail> list);

    int batchInsert(List<OpeProductionDeliveryDetail> list);

    int insertOrUpdate(OpeProductionDeliveryDetail record);

    int insertOrUpdateSelective(OpeProductionDeliveryDetail record);

}
