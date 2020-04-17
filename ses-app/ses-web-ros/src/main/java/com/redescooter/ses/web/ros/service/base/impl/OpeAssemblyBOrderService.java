package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeAssemblyBOrderService extends IService<OpeAssemblyBOrder> {


    int updateBatch(List<OpeAssemblyBOrder> list);

    int updateBatchSelective(List<OpeAssemblyBOrder> list);

    int batchInsert(List<OpeAssemblyBOrder> list);

    int insertOrUpdate(OpeAssemblyBOrder record);

    int insertOrUpdateSelective(OpeAssemblyBOrder record);

}

