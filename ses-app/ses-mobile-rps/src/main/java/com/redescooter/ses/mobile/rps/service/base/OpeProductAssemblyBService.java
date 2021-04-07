package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssemblyB;

import java.util.List;

public interface OpeProductAssemblyBService extends IService<OpeProductAssemblyB>{


    int updateBatch(List<OpeProductAssemblyB> list);

    int batchInsert(List<OpeProductAssemblyB> list);

    int insertOrUpdate(OpeProductAssemblyB record);

    int insertOrUpdateSelective(OpeProductAssemblyB record);

}
