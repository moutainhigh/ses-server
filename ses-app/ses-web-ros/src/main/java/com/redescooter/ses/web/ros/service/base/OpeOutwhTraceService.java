package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOutwhTrace;

import java.util.List;

public interface OpeOutwhTraceService extends IService<OpeOutwhTrace> {


    int updateBatch(List<OpeOutwhTrace> list);

    int batchInsert(List<OpeOutwhTrace> list);

    int insertOrUpdate(OpeOutwhTrace record);

    int insertOrUpdateSelective(OpeOutwhTrace record);

}



