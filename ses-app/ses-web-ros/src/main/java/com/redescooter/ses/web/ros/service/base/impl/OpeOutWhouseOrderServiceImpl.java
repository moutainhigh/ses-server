package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhouseOrderMapper;
import com.redescooter.ses.web.ros.service.base.OpeOutWhouseOrderService;

@Service
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
}
