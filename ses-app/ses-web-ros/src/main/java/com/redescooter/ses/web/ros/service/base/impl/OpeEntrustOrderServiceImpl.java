package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustOrderMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeEntrustOrder;
import com.redescooter.ses.web.ros.service.base.OpeEntrustOrderService;

@Service
public class OpeEntrustOrderServiceImpl extends ServiceImpl<OpeEntrustOrderMapper, OpeEntrustOrder> implements OpeEntrustOrderService {

    @Override
    public int updateBatch(List<OpeEntrustOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeEntrustOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeEntrustOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeEntrustOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
