package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeAllocateCombinBMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateCombinB;
import com.redescooter.ses.web.ros.service.base.OpeAllocateCombinBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
