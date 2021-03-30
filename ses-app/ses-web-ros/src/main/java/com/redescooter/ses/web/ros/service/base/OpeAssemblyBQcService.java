package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBQc;

import java.util.List;

public interface OpeAssemblyBQcService extends IService<OpeAssemblyBQc>{


    int updateBatch(List<OpeAssemblyBQc> list);

    int batchInsert(List<OpeAssemblyBQc> list);

    int insertOrUpdate(OpeAssemblyBQc record);

    int insertOrUpdateSelective(OpeAssemblyBQc record);

}
