package com.redescooter.ses.service.mobile.b.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.mobile.b.dm.base.CorTenantScooter;
import com.redescooter.ses.service.mobile.b.dao.base.CorTenantScooterMapper;
import com.redescooter.ses.service.mobile.b.service.base.CorTenantScooterService;
@Service
public class CorTenantScooterServiceImpl extends ServiceImpl<CorTenantScooterMapper, CorTenantScooter> implements CorTenantScooterService{

    @Override
    public int updateBatch(List<CorTenantScooter> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<CorTenantScooter> list) {
        return baseMapper.updateBatchSelective(list);
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
