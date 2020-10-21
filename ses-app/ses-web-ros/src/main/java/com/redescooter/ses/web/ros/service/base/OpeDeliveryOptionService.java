package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeDeliveryOption;

import java.util.List;

public interface OpeDeliveryOptionService extends IService<OpeDeliveryOption>{


    int updateBatch(List<OpeDeliveryOption> list);

    int updateBatchSelective(List<OpeDeliveryOption> list);

    int batchInsert(List<OpeDeliveryOption> list);

    int insertOrUpdate(OpeDeliveryOption record);

    int insertOrUpdateSelective(OpeDeliveryOption record);

}
