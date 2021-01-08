package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteOrderB;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SiteOrderBService extends IService<SiteOrderB>{


    int updateBatch(List<SiteOrderB> list);

    int updateBatchSelective(List<SiteOrderB> list);

    int batchInsert(List<SiteOrderB> list);

    int insertOrUpdate(SiteOrderB record);

    int insertOrUpdateSelective(SiteOrderB record);

}
