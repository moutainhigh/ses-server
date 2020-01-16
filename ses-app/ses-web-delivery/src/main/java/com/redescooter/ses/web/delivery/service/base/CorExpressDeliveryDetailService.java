package com.redescooter.ses.web.delivery.service.base;

import java.util.List;

import com.redescooter.ses.web.delivery.dm.CorExpressDeliveryDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CorExpressDeliveryDetailService extends IService<CorExpressDeliveryDetail> {


    int updateBatch(List<CorExpressDeliveryDetail> list);

    int updateBatchSelective(List<CorExpressDeliveryDetail> list);

    int batchInsert(List<CorExpressDeliveryDetail> list);

    int insertOrUpdate(CorExpressDeliveryDetail record);

    int insertOrUpdateSelective(CorExpressDeliveryDetail record);

}
