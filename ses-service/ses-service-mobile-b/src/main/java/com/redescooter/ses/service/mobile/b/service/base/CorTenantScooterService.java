package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorTenantScooter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CorTenantScooterService extends IService<CorTenantScooter> {


    int updateBatch(List<CorTenantScooter> list);

    int updateBatchSelective(List<CorTenantScooter> list);

    int batchInsert(List<CorTenantScooter> list);

    int insertOrUpdate(CorTenantScooter record);

    int insertOrUpdateSelective(CorTenantScooter record);

}
