package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeOrderStatusFlow;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOrderStatusFlowService extends IService<OpeOrderStatusFlow> {


    int updateBatch(List<OpeOrderStatusFlow> list);

    int batchInsert(List<OpeOrderStatusFlow> list);

    int insertOrUpdate(OpeOrderStatusFlow record);

    int insertOrUpdateSelective(OpeOrderStatusFlow record);

}
