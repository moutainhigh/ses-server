package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssemblyB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductAssemblyBService extends IService<OpeProductAssemblyB> {


    int updateBatch(List<OpeProductAssemblyB> list);

    int batchInsert(List<OpeProductAssemblyB> list);

    int insertOrUpdate(OpeProductAssemblyB record);

    int insertOrUpdateSelective(OpeProductAssemblyB record);

}





