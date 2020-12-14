package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeOutwhTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeOutwhTraceService extends IService<OpeOutwhTrace> {


    int updateBatch(List<OpeOutwhTrace> list);

    int batchInsert(List<OpeOutwhTrace> list);

    int insertOrUpdate(OpeOutwhTrace record);

    int insertOrUpdateSelective(OpeOutwhTrace record);

}



