package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import com.redescooter.ses.service.mobile.b.service.base.impl.CorDriverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorDriverServiceImpl extends ServiceImpl<CorDriverMapper, CorDriver> implements CorDriverService {

    @Override
    public int updateBatch(List<CorDriver> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorDriver> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDriver record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDriver record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

