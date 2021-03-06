package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAllocateOrder;

import java.util.List;

public interface OpeAllocateOrderService extends IService<OpeAllocateOrder> {


    int updateBatch(List<OpeAllocateOrder> list);

    int batchInsert(List<OpeAllocateOrder> list);

    int insertOrUpdate(OpeAllocateOrder record);

    int insertOrUpdateSelective(OpeAllocateOrder record);

}


