package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeRepairOrderStaffMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairOrderStaff;
import com.redescooter.ses.web.ros.service.base.OpeRepairOrderStaffService;

@Service
public class OpeRepairOrderStaffServiceImpl extends ServiceImpl<OpeRepairOrderStaffMapper, OpeRepairOrderStaff> implements OpeRepairOrderStaffService {

    @Override
    public int updateBatch(List<OpeRepairOrderStaff> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairOrderStaff> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairOrderStaff record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairOrderStaff record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
