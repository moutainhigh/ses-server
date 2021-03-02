package com.redescooter.ses.admin.dev.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.admin.dev.dao.base.AdmSysUserMapper;
import com.redescooter.ses.admin.dev.dm.AdmSysUser;
import com.redescooter.ses.admin.dev.service.base.AdmSysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmSysUserServiceImpl extends ServiceImpl<AdmSysUserMapper, AdmSysUser> implements AdmSysUserService {

    @Override
    public int updateBatch(List<AdmSysUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<AdmSysUser> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<AdmSysUser> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(AdmSysUser record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(AdmSysUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
