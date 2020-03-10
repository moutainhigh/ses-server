package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeFactoryTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeFactoryTraceService extends IService<OpeFactoryTrace> {


    int updateBatch(List<OpeFactoryTrace> list);

    int batchInsert(List<OpeFactoryTrace> list);

    int insertOrUpdate(OpeFactoryTrace record);

    int insertOrUpdateSelective(OpeFactoryTrace record);

}
