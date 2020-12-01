package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeAllocateScooterBMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateScooterB;
import com.redescooter.ses.web.ros.service.base.OpeAllocateScooterBService;

@Service
public class OpeAllocateScooterBServiceImpl extends ServiceImpl<OpeAllocateScooterBMapper, OpeAllocateScooterB> implements OpeAllocateScooterBService {

    @Override
    public int updateBatch(List<OpeAllocateScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
