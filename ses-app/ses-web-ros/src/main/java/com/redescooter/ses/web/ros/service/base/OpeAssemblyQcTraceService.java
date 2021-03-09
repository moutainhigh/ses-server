package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcTrace;

import java.util.List;

public interface OpeAssemblyQcTraceService extends IService<OpeAssemblyQcTrace> {


    int updateBatch(List<OpeAssemblyQcTrace> list);

    int batchInsert(List<OpeAssemblyQcTrace> list);

    int insertOrUpdate(OpeAssemblyQcTrace record);

    int insertOrUpdateSelective(OpeAssemblyQcTrace record);

}


