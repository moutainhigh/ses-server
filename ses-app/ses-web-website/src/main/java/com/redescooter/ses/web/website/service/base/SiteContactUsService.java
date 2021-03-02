package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteContactUs;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteContactUsService extends IService<SiteContactUs> {


    int updateBatch(List<SiteContactUs> list);

    int updateBatchSelective(List<SiteContactUs> list);

    int batchInsert(List<SiteContactUs> list);

    int insertOrUpdate(SiteContactUs record);

    int insertOrUpdateSelective(SiteContactUs record);

}

