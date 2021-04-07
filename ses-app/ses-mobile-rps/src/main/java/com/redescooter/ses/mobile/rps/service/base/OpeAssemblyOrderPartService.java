package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrderPart;

import java.util.List;

public interface OpeAssemblyOrderPartService extends IService<OpeAssemblyOrderPart>{


    int updateBatch(List<OpeAssemblyOrderPart> list);

    int batchInsert(List<OpeAssemblyOrderPart> list);

    int insertOrUpdate(OpeAssemblyOrderPart record);

    int insertOrUpdateSelective(OpeAssemblyOrderPart record);

}
