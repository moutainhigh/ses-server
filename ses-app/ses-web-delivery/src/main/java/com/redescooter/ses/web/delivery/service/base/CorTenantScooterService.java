package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;

import java.util.List;
public interface CorTenantScooterService extends IService<CorTenantScooter>{


    int updateBatch(List<CorTenantScooter> list);

    int batchInsert(List<CorTenantScooter> list);

    int insertOrUpdate(CorTenantScooter record);

    int insertOrUpdateSelective(CorTenantScooter record);

}
