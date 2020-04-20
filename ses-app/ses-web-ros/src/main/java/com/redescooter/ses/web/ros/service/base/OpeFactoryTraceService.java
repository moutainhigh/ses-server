package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeFactoryTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeFactoryTraceService extends IService<OpeFactoryTrace>{


    int updateBatch(List<OpeFactoryTrace> list);

    int batchInsert(List<OpeFactoryTrace> list);

    int insertOrUpdate(OpeFactoryTrace record);

    int insertOrUpdateSelective(OpeFactoryTrace record);

}
