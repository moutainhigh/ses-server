package com.redescooter.ses.web.ros.service.base.impl;

import com.redescooter.ses.web.ros.dm.OpeAssemblyBOrder;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeAssemblyBOrderMapper;

import com.redescooter.ses.web.ros.service.base.OpeAssemblyBOrderService;

@Service
public class OpeAssemblyBOrderServiceImpl extends ServiceImpl<OpeAssemblyBOrderMapper, OpeAssemblyBOrder> implements OpeAssemblyBOrderService {

    @Override
    public int batchInsert(List<OpeAssemblyBOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyBOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyBOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatch(List<OpeAssemblyBOrder> list) {
        return baseMapper.updateBatch(list);
    }
}



