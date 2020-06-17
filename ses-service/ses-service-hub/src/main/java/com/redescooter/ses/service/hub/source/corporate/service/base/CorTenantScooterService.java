package com.redescooter.ses.service.hub.source.corporate.service.base;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.corporate.dm.CorTenantScooter;
import com.baomidou.mybatisplus.extension.service.IService;
@DS("corporate")
public interface CorTenantScooterService extends IService<CorTenantScooter> {


    int updateBatch(List<CorTenantScooter> list);

    int batchInsert(List<CorTenantScooter> list);

    int insertOrUpdate(CorTenantScooter record);

    int insertOrUpdateSelective(CorTenantScooter record);

}

