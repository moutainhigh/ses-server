package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderScooter;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeRepairOrderScooterMapper;
import com.redescooter.ses.web.ros.service.base.OpeRepairOrderScooterService;

@Service
public class OpeRepairOrderScooterServiceImpl extends ServiceImpl<OpeRepairOrderScooterMapper, OpeRepairOrderScooter> implements OpeRepairOrderScooterService {

    @Override
    public int updateBatch(List<OpeRepairOrderScooter> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairOrderScooter> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairOrderScooter record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairOrderScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
