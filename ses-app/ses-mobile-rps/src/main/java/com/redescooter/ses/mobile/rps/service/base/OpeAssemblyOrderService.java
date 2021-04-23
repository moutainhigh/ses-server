package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;

import java.util.List;

public interface OpeAssemblyOrderService extends IService<OpeAssemblyOrder>{


    int updateBatch(List<OpeAssemblyOrder> list);

    int batchInsert(List<OpeAssemblyOrder> list);

    int insertOrUpdate(OpeAssemblyOrder record);

    int insertOrUpdateSelective(OpeAssemblyOrder record);

}
