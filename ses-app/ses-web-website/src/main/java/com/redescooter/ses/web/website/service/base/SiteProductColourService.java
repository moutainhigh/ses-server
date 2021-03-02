package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteProductColour;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteProductColourService extends IService<SiteProductColour> {

    int updateBatch(List<SiteProductColour> list);

    int updateBatchSelective(List<SiteProductColour> list);

    int batchInsert(List<SiteProductColour> list);

    int insertOrUpdate(SiteProductColour record);

    int insertOrUpdateSelective(SiteProductColour record);

}

