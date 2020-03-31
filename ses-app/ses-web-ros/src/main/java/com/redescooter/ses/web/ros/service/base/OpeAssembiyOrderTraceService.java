package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAssembiyOrderTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeAssembiyOrderTraceService extends IService<OpeAssembiyOrderTrace> {


    int updateBatch(List<OpeAssembiyOrderTrace> list);

    int batchInsert(List<OpeAssembiyOrderTrace> list);

    int insertOrUpdate(OpeAssembiyOrderTrace record);

    int insertOrUpdateSelective(OpeAssembiyOrderTrace record);

}


