package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssemblyQcTraceService extends IService<OpeAssemblyQcTrace> {


    int updateBatch(List<OpeAssemblyQcTrace> list);

    int batchInsert(List<OpeAssemblyQcTrace> list);

    int insertOrUpdate(OpeAssemblyQcTrace record);

    int insertOrUpdateSelective(OpeAssemblyQcTrace record);

}

