package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeOrderStatusFlowMapper;
import com.redescooter.ses.web.ros.dm.OpeOrderStatusFlow;

import java.util.List;

import com.redescooter.ses.web.ros.service.base.OpeOrderStatusFlowService;

@Service
public class OpeOrderStatusFlowServiceImpl extends ServiceImpl<OpeOrderStatusFlowMapper, OpeOrderStatusFlow> implements OpeOrderStatusFlowService {

    @Override
    public int updateBatch(List<OpeOrderStatusFlow> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrderStatusFlow> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrderStatusFlow record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrderStatusFlow record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
