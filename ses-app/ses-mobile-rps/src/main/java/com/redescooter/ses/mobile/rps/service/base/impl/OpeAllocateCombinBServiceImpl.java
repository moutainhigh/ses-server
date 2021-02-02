package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocateCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateCombinB;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateCombinBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OpeAllocateCombinBServiceImpl extends ServiceImpl<OpeAllocateCombinBMapper, OpeAllocateCombinB> implements OpeAllocateCombinBService {

    @Override
    public int updateBatch(List<OpeAllocateCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
