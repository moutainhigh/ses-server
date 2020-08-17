package com.redescooter.ses.web.ros.service.base.impl;

import com.redescooter.ses.web.ros.dm.OpeOutwhOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeOutwhOrderMapper;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderService;

@Service
public class OpeOutwhOrderServiceImpl extends ServiceImpl<OpeOutwhOrderMapper, OpeOutwhOrder> implements OpeOutwhOrderService {

    @Override
    public int updateBatch(List<OpeOutwhOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutwhOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutwhOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutwhOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}








