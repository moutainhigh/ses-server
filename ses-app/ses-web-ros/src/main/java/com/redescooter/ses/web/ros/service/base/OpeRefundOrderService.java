package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeRefundOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRefundOrderService extends IService<OpeRefundOrder> {

    int updateBatch(List<OpeRefundOrder> list);

    int batchInsert(List<OpeRefundOrder> list);

    int insertOrUpdate(OpeRefundOrder record);

    int insertOrUpdateSelective(OpeRefundOrder record);

}
