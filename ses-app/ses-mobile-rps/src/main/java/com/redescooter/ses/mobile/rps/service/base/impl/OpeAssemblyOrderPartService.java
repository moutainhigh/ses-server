package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrderPart;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeAssemblyOrderPartService extends IService<OpeAssemblyOrderPart>{


    int updateBatch(List<OpeAssemblyOrderPart> list);

    int batchInsert(List<OpeAssemblyOrderPart> list);

    int insertOrUpdate(OpeAssemblyOrderPart record);

    int insertOrUpdateSelective(OpeAssemblyOrderPart record);

}
