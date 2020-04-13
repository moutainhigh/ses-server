package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairOrderTraceService extends IService<OpeRepairOrderTrace> {


    int updateBatch(List<OpeRepairOrderTrace> list);

    int batchInsert(List<OpeRepairOrderTrace> list);

    int insertOrUpdate(OpeRepairOrderTrace record);

    int insertOrUpdateSelective(OpeRepairOrderTrace record);

}
