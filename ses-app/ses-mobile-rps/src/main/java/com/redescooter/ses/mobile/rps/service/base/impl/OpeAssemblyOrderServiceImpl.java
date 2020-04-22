package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAssemblyOrderMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAssemblyOrderServiceImpl extends ServiceImpl<OpeAssemblyOrderMapper, OpeAssemblyOrder> implements OpeAssemblyOrderService {

    @Override
    public int updateBatch(List<OpeAssemblyOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}








