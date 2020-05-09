package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeOrgStaff;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeOrgStaffMapper;
import com.redescooter.ses.web.ros.service.base.OpeOrgStaffService;

@Service
public class OpeOrgStaffServiceImpl extends ServiceImpl<OpeOrgStaffMapper, OpeOrgStaff> implements OpeOrgStaffService {

    @Override
    public int updateBatch(List<OpeOrgStaff> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrgStaff> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrgStaff record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrgStaff record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

