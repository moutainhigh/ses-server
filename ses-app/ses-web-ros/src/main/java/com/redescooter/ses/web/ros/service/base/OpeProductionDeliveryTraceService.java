package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductionDeliveryTraceService extends IService<OpeProductionDeliveryTrace> {


    int updateBatch(List<OpeProductionDeliveryTrace> list);

    int batchInsert(List<OpeProductionDeliveryTrace> list);

    int insertOrUpdate(OpeProductionDeliveryTrace record);

    int insertOrUpdateSelective(OpeProductionDeliveryTrace record);

}
