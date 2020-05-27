package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpePayOrder;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePayOrderService extends IService<OpePayOrder> {

    int updateBatch(List<OpePayOrder> list);

    int batchInsert(List<OpePayOrder> list);

    int insertOrUpdate(OpePayOrder record);

    int insertOrUpdateSelective(OpePayOrder record);

}
