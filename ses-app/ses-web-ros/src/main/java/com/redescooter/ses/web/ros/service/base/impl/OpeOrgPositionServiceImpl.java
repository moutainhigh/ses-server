package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeOrgPositionMapper;
import com.redescooter.ses.web.ros.dm.OpeOrgPosition;

import java.util.List;

import com.redescooter.ses.web.ros.service.base.OpeOrgPositionService;

@Service
public class OpeOrgPositionServiceImpl extends ServiceImpl<OpeOrgPositionMapper, OpeOrgPosition> implements OpeOrgPositionService {

    @Override
    public int updateBatch(List<OpeOrgPosition> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrgPosition> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrgPosition record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrgPosition record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
