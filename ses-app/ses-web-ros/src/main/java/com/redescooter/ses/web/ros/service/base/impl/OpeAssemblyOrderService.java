package com.redescooter.ses.web.ros.service.base.impl;

import com.redescooter.ses.web.ros.dm.OpeAssemblyOrder;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeAssemblyOrderService extends IService<OpeAssemblyOrder>{


    int updateBatch(List<OpeAssemblyOrder> list);

    int batchInsert(List<OpeAssemblyOrder> list);

    int insertOrUpdate(OpeAssemblyOrder record);

    int insertOrUpdateSelective(OpeAssemblyOrder record);

}
