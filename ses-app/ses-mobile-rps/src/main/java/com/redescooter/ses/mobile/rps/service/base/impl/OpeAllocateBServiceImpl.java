package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocateBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateB;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAllocateBServiceImpl extends ServiceImpl<OpeAllocateBMapper, OpeAllocateB> implements OpeAllocateBService {

    @Override
    public int updateBatch(List<OpeAllocateB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}





