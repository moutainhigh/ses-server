package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeAssembiyOrderTrace;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssembiyOrderTraceService extends IService<OpeAssembiyOrderTrace> {


    int updateBatch(List<OpeAssembiyOrderTrace> list);

    int batchInsert(List<OpeAssembiyOrderTrace> list);

    int insertOrUpdate(OpeAssembiyOrderTrace record);

    int insertOrUpdateSelective(OpeAssembiyOrderTrace record);

}


