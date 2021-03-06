package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssembly;

import java.util.List;

public interface OpeProductAssemblyService extends IService<OpeProductAssembly> {


    int updateBatch(List<OpeProductAssembly> list);

    int batchInsert(List<OpeProductAssembly> list);

    int insertOrUpdate(OpeProductAssembly record);

    int insertOrUpdateSelective(OpeProductAssembly record);

}



