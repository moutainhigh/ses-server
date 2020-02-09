package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTrace;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTraceExample;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional

public interface CorExpressOrderTraceService extends IService<CorExpressOrderTrace>{


    long countByExample(CorExpressOrderTraceExample example);

    int deleteByExample(CorExpressOrderTraceExample example);

    List<CorExpressOrderTrace> selectByExample(CorExpressOrderTraceExample example);

    int updateByExampleSelective(CorExpressOrderTrace record,CorExpressOrderTraceExample example);

    int updateByExample(CorExpressOrderTrace record,CorExpressOrderTraceExample example);

}
