package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeAllocateOrder;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeAllocateOrderService extends IService<OpeAllocateOrder> {


    int updateBatch(List<OpeAllocateOrder> list);

    int batchInsert(List<OpeAllocateOrder> list);

    int insertOrUpdate(OpeAllocateOrder record);

    int insertOrUpdateSelective(OpeAllocateOrder record);

}
