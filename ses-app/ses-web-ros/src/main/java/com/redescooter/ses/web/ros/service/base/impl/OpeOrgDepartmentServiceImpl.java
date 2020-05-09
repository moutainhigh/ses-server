package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeOrgDepartmentMapper;
import com.redescooter.ses.web.ros.dm.OpeOrgDepartment;
import com.redescooter.ses.web.ros.service.base.OpeOrgDepartmentService;

@Service
public class OpeOrgDepartmentServiceImpl extends ServiceImpl<OpeOrgDepartmentMapper, OpeOrgDepartment> implements OpeOrgDepartmentService {

    @Override
    public int updateBatch(List<OpeOrgDepartment> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrgDepartment> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrgDepartment record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrgDepartment record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
