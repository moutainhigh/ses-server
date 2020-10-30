package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOutWhouseOrderService extends IService<OpeOutWhouseOrder> {


    int updateBatch(List<OpeOutWhouseOrder> list);

    int batchInsert(List<OpeOutWhouseOrder> list);

    int insertOrUpdate(OpeOutWhouseOrder record);

    int insertOrUpdateSelective(OpeOutWhouseOrder record);

}
