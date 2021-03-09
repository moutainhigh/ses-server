package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductAssembly;

import java.util.List;

public interface OpeProductAssemblyService extends IService<OpeProductAssembly>{


    int updateBatch(List<OpeProductAssembly> list);

    int batchInsert(List<OpeProductAssembly> list);

    int insertOrUpdate(OpeProductAssembly record);

    int insertOrUpdateSelective(OpeProductAssembly record);

}
