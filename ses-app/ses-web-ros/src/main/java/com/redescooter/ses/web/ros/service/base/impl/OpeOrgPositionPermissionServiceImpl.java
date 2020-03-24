package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOrgPositionPermission;
import com.redescooter.ses.web.ros.dao.base.OpeOrgPositionPermissionMapper;
import com.redescooter.ses.web.ros.service.base.OpeOrgPositionPermissionService;

@Service
public class OpeOrgPositionPermissionServiceImpl extends ServiceImpl<OpeOrgPositionPermissionMapper, OpeOrgPositionPermission> implements OpeOrgPositionPermissionService {

    @Override
    public int updateBatch(List<OpeOrgPositionPermission> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrgPositionPermission> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrgPositionPermission record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrgPositionPermission record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
