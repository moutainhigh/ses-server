package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocatePartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocatePartsB;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocatePartsBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeAllocatePartsBServiceImpl extends ServiceImpl<OpeAllocatePartsBMapper, OpeAllocatePartsB> implements OpeAllocatePartsBService {

    @Override
    public int updateBatch(List<OpeAllocatePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocatePartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocatePartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocatePartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
