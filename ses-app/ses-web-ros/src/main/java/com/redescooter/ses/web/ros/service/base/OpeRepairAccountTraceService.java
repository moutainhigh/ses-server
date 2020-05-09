package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairAccountTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairAccountTraceService extends IService<OpeRepairAccountTrace> {


    int updateBatch(List<OpeRepairAccountTrace> list);

    int batchInsert(List<OpeRepairAccountTrace> list);

    int insertOrUpdate(OpeRepairAccountTrace record);

    int insertOrUpdateSelective(OpeRepairAccountTrace record);

}
