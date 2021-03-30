package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductAssemblyB;

import java.util.List;

public interface OpeProductAssemblyBService extends IService<OpeProductAssemblyB>{


    int updateBatch(List<OpeProductAssemblyB> list);

    int batchInsert(List<OpeProductAssemblyB> list);

    int insertOrUpdate(OpeProductAssemblyB record);

    int insertOrUpdateSelective(OpeProductAssemblyB record);

}
