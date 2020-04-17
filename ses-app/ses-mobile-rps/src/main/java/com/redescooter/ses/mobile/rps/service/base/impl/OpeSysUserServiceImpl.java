package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeSysUserMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysUser;
import com.redescooter.ses.mobile.rps.service.base.impl.OpeSysUserService;

@Service
public class OpeSysUserServiceImpl extends ServiceImpl<OpeSysUserMapper, OpeSysUser> implements OpeSysUserService {

    @Override
    public int updateBatch(List<OpeSysUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysUser> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysUser record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

