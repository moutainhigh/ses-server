package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeOutWhCombinBMapper;
import com.redescooter.ses.web.ros.service.base.OpeOutWhCombinBService;

@Service
public class OpeOutWhCombinBServiceImpl extends ServiceImpl<OpeOutWhCombinBMapper, OpeOutWhCombinB> implements OpeOutWhCombinBService {

    @Override
    public int updateBatch(List<OpeOutWhCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


