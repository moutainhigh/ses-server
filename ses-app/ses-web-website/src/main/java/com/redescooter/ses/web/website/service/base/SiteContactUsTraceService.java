package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteContactUsTrace;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SiteContactUsTraceService extends IService<SiteContactUsTrace>{


    int updateBatch(List<SiteContactUsTrace> list);

    int updateBatchSelective(List<SiteContactUsTrace> list);

    int batchInsert(List<SiteContactUsTrace> list);

    int insertOrUpdate(SiteContactUsTrace record);

    int insertOrUpdateSelective(SiteContactUsTrace record);

}
