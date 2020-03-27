package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesOrderService extends IService<OpeSalesOrder> {


    int updateBatch(List<OpeSalesOrder> list);

    int batchInsert(List<OpeSalesOrder> list);

    int insertOrUpdate(OpeSalesOrder record);

    int insertOrUpdateSelective(OpeSalesOrder record);

}
