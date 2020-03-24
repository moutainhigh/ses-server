package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairOrderTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairOrderTraceService extends IService<OpeRepairOrderTrace> {


    int updateBatch(List<OpeRepairOrderTrace> list);

    int batchInsert(List<OpeRepairOrderTrace> list);

    int insertOrUpdate(OpeRepairOrderTrace record);

    int insertOrUpdateSelective(OpeRepairOrderTrace record);

}
