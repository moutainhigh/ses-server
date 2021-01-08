package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteUserService extends IService<SiteUser> {


    int updateBatch(List<SiteUser> list);

    int updateBatchSelective(List<SiteUser> list);

    int batchInsert(List<SiteUser> list);

    int insertOrUpdate(SiteUser record);

    int insertOrUpdateSelective(SiteUser record);

}

