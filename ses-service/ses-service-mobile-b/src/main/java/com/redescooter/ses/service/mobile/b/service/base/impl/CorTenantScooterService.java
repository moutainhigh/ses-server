package com.redescooter.ses.service.mobile.b.service.base.impl;

import java.util.List;
import com.redescooter.ses.service.mobile.b.dm.base.CorTenantScooter;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorTenantScooterService extends IService<CorTenantScooter>{


    int updateBatch(List<CorTenantScooter> list);

    int batchInsert(List<CorTenantScooter> list);

    int insertOrUpdate(CorTenantScooter record);

    int insertOrUpdateSelective(CorTenantScooter record);

}
