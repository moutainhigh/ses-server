package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorTenantScooterMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorTenantScooter;
import com.redescooter.ses.service.mobile.b.service.base.impl.CorTenantScooterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorTenantScooterServiceImpl extends ServiceImpl<CorTenantScooterMapper, CorTenantScooter> implements CorTenantScooterService {

    @Override
    public int updateBatch(List<CorTenantScooter> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorTenantScooter> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorTenantScooter record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorTenantScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
