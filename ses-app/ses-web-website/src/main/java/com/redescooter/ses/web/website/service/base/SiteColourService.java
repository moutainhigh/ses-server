package com.redescooter.ses.web.website.service.base;

import com.redescooter.ses.web.website.dm.SiteColour;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteColourService extends IService<SiteColour> {


    int updateBatch(List<SiteColour> list);

    int updateBatchSelective(List<SiteColour> list);

    int batchInsert(List<SiteColour> list);

    int insertOrUpdate(SiteColour record);

    int insertOrUpdateSelective(SiteColour record);

}

