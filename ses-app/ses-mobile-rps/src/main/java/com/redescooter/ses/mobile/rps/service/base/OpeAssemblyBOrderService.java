package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;

import java.util.List;

public interface OpeAssemblyBOrderService extends IService<OpeAssemblyBOrder>{


    int updateBatch(List<OpeAssemblyBOrder> list);

    int batchInsert(List<OpeAssemblyBOrder> list);

    int insertOrUpdate(OpeAssemblyBOrder record);

    int insertOrUpdateSelective(OpeAssemblyBOrder record);

}
