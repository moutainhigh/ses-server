package com.redescooter.ses.web.website.service.base;

import com.redescooter.ses.web.website.dm.SiteParts;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SitePartsService extends IService<SiteParts>{


    int updateBatch(List<SiteParts> list);

    int updateBatchSelective(List<SiteParts> list);

    int batchInsert(List<SiteParts> list);

    int insertOrUpdate(SiteParts record);

    int insertOrUpdateSelective(SiteParts record);

}
