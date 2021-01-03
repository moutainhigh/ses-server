package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteProductClass;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SiteProductClassService extends IService<SiteProductClass>{


    int updateBatch(List<SiteProductClass> list);

    int updateBatchSelective(List<SiteProductClass> list);

    int batchInsert(List<SiteProductClass> list);

    int insertOrUpdate(SiteProductClass record);

    int insertOrUpdateSelective(SiteProductClass record);

}
