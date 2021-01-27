package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOutWhouseOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhouseOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeOutWhouseOrderServiceImpl extends ServiceImpl<OpeOutWhouseOrderMapper, OpeOutWhouseOrder> implements OpeOutWhouseOrderService {

    @Override
    public int updateBatch(List<OpeOutWhouseOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhouseOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhouseOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhouseOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return opeOutWhouseOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeOutWhouseOrder record) {
        return opeOutWhouseOrderMapper.insertSelective(record);
    }

    @Override
    public OpeOutWhouseOrder selectByPrimaryKey(Integer id) {
        return opeOutWhouseOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeOutWhouseOrder record) {
        return opeOutWhouseOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeOutWhouseOrder record) {
        return opeOutWhouseOrderMapper.updateByPrimaryKey(record);
    }
}


