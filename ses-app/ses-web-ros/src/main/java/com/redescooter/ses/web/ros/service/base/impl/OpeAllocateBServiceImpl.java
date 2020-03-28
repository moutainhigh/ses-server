package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeAllocateBMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateB;
import com.redescooter.ses.web.ros.service.base.OpeAllocateBService;

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

