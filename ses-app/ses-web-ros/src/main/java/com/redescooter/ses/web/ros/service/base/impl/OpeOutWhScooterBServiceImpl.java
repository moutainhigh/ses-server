package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhScooterBMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;
import com.redescooter.ses.web.ros.service.base.OpeOutWhScooterBService;

@Service
public class OpeOutWhScooterBServiceImpl extends ServiceImpl<OpeOutWhScooterBMapper, OpeOutWhScooterB> implements OpeOutWhScooterBService {

    @Override
    public int updateBatch(List<OpeOutWhScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
