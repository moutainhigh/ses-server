package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeAllocatePartsB;
import com.redescooter.ses.web.ros.dao.base.OpeAllocatePartsBMapper;
import com.redescooter.ses.web.ros.service.base.OpeAllocatePartsBService;

@Service
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
