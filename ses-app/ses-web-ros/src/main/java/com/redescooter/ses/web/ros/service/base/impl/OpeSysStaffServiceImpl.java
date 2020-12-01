package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysStaffMapper;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSysStaffServiceImpl extends ServiceImpl<OpeSysStaffMapper, OpeSysStaff> implements OpeSysStaffService {

    @Override
    public int updateBatch(List<OpeSysStaff> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysStaff> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysStaff record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysStaff record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatchSelective(List<OpeSysStaff> list) {
        return 0;
    }
}






