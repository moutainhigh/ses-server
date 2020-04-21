package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeProductAssembly;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeProductAssemblyService extends IService<OpeProductAssembly>{


    int updateBatch(List<OpeProductAssembly> list);

    int batchInsert(List<OpeProductAssembly> list);

    int insertOrUpdate(OpeProductAssembly record);

    int insertOrUpdateSelective(OpeProductAssembly record);

}
