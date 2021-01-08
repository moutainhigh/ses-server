package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteOrder;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SiteOrderService extends IService<SiteOrder>{


    int updateBatch(List<SiteOrder> list);

    int updateBatchSelective(List<SiteOrder> list);

    int batchInsert(List<SiteOrder> list);

    int insertOrUpdate(SiteOrder record);

    int insertOrUpdateSelective(SiteOrder record);

}
