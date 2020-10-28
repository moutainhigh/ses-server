package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOpTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeOpTraceService extends IService<OpeOpTrace> {


    int updateBatch(List<OpeOpTrace> list);

    int batchInsert(List<OpeOpTrace> list);

    int insertOrUpdate(OpeOpTrace record);

    int insertOrUpdateSelective(OpeOpTrace record);

}


