package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeOrgStaffPositionMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOrgStaffPosition;
import com.redescooter.ses.web.ros.service.base.OpeOrgStaffPositionService;

@Service
public class OpeOrgStaffPositionServiceImpl extends ServiceImpl<OpeOrgStaffPositionMapper, OpeOrgStaffPosition> implements OpeOrgStaffPositionService {

    @Override
    public int updateBatch(List<OpeOrgStaffPosition> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrgStaffPosition> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrgStaffPosition record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrgStaffPosition record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
