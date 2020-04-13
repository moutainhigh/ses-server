package com.redescooter.ses.web.delivery.service.base;

import java.util.List;

import com.redescooter.ses.web.delivery.dm.CorExpressOrderTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CorExpressOrderTraceService extends IService<CorExpressOrderTrace> {


    int batchInsert(List<CorExpressOrderTrace> list);

    int insertOrUpdate(CorExpressOrderTrace record);

    int insertOrUpdateSelective(CorExpressOrderTrace record);

}
