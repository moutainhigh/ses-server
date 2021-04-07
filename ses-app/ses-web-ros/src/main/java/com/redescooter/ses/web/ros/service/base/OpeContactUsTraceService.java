package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeContactUsTrace;

import java.util.List;

public interface OpeContactUsTraceService extends IService<OpeContactUsTrace> {


    int updateBatch(List<OpeContactUsTrace> list);

    int batchInsert(List<OpeContactUsTrace> list);

    int insertOrUpdate(OpeContactUsTrace record);

    int insertOrUpdateSelective(OpeContactUsTrace record);

}





