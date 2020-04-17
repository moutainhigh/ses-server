package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysStaffMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
@Service
public class OpeSysStaffServiceImpl extends ServiceImpl<OpeSysStaffMapper, OpeSysStaff> implements OpeSysStaffService{

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
}
