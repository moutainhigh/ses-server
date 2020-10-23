package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeOutWhPartsBMapper;
import com.redescooter.ses.web.ros.dm.OpeOutWhPartsB;
import com.redescooter.ses.web.ros.service.base.OpeOutWhPartsBService;

@Service
public class OpeOutWhPartsBServiceImpl extends ServiceImpl<OpeOutWhPartsBMapper, OpeOutWhPartsB> implements OpeOutWhPartsBService {

    @Override
    public int updateBatch(List<OpeOutWhPartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhPartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhPartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhPartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
