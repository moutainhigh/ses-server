package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteDistributor;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SiteDistributorService extends IService<SiteDistributor>{


    int updateBatch(List<SiteDistributor> list);

    int updateBatchSelective(List<SiteDistributor> list);

    int batchInsert(List<SiteDistributor> list);

    int insertOrUpdate(SiteDistributor record);

    int insertOrUpdateSelective(SiteDistributor record);

}
