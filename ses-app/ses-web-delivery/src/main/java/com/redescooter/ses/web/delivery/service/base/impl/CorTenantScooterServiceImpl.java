package com.redescooter.ses.web.delivery.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorTenantScooterMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 11:22 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
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

    @Override
    public int updateBatchSelective(List<CorTenantScooter> list) {
        return baseMapper.updateBatchSelective(list);
    }
}



