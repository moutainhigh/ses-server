package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSaleScooterColorMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleScooterColor;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterColorService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeSaleScooterColorServiceImpl extends ServiceImpl<OpeSaleScooterColorMapper, OpeSaleScooterColor> implements OpeSaleScooterColorService{

    @Override
    public int batchInsert(List<OpeSaleScooterColor> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeSaleScooterColor record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeSaleScooterColor record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
