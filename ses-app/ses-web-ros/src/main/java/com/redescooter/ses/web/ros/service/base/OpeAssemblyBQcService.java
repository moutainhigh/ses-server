package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBQc;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeAssemblyBQcService extends IService<OpeAssemblyBQc>{


    int updateBatch(List<OpeAssemblyBQc> list);

    int batchInsert(List<OpeAssemblyBQc> list);

    int insertOrUpdate(OpeAssemblyBQc record);

    int insertOrUpdateSelective(OpeAssemblyBQc record);

}
