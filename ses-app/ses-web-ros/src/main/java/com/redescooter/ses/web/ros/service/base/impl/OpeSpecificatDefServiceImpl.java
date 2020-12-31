package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatDefMapper;
import com.redescooter.ses.web.ros.dm.OpeSpecificatDef;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatDefService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSpecificatDefServiceImpl extends ServiceImpl<OpeSpecificatDefMapper, OpeSpecificatDef> implements OpeSpecificatDefService {

    @Override
    public int updateBatch(List<OpeSpecificatDef> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSpecificatDef> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSpecificatDef record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSpecificatDef record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

