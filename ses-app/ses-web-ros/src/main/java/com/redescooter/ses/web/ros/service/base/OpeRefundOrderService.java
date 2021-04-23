package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRefundOrder;

import java.util.List;

public interface OpeRefundOrderService extends IService<OpeRefundOrder> {

    int updateBatch(List<OpeRefundOrder> list);

    int batchInsert(List<OpeRefundOrder> list);

    int insertOrUpdate(OpeRefundOrder record);

    int insertOrUpdateSelective(OpeRefundOrder record);

}
