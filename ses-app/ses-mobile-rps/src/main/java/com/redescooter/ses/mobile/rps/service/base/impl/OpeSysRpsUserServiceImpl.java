package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeSysRpsUserMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysRpsUser;
import com.redescooter.ses.mobile.rps.service.base.OpeSysRpsUserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeSysRpsUserServiceImpl extends ServiceImpl<OpeSysRpsUserMapper, OpeSysRpsUser> implements OpeSysRpsUserService{

    @Override
    public int updateBatch(List<OpeSysRpsUser> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeSysRpsUser> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeSysRpsUser record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeSysRpsUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
