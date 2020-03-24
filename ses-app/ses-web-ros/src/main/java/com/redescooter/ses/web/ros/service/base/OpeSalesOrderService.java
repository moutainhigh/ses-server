package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesOrderService extends IService<OpeSalesOrder> {


    int updateBatch(List<OpeSalesOrder> list);

    int batchInsert(List<OpeSalesOrder> list);

    int insertOrUpdate(OpeSalesOrder record);

    int insertOrUpdateSelective(OpeSalesOrder record);

}
