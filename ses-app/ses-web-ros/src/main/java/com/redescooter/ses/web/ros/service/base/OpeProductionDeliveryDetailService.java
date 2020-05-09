package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductionDeliveryDetailService extends IService<OpeProductionDeliveryDetail> {


    int updateBatch(List<OpeProductionDeliveryDetail> list);

    int batchInsert(List<OpeProductionDeliveryDetail> list);

    int insertOrUpdate(OpeProductionDeliveryDetail record);

    int insertOrUpdateSelective(OpeProductionDeliveryDetail record);

}
