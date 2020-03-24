package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairAccountTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairAccountTraceService extends IService<OpeRepairAccountTrace> {


    int updateBatch(List<OpeRepairAccountTrace> list);

    int batchInsert(List<OpeRepairAccountTrace> list);

    int insertOrUpdate(OpeRepairAccountTrace record);

    int insertOrUpdateSelective(OpeRepairAccountTrace record);

}
