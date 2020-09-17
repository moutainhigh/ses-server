package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeSysRoleDataMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleData;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDataService;

@Service
public class OpeSysRoleDataServiceImpl extends ServiceImpl<OpeSysRoleDataMapper, OpeSysRoleData> implements OpeSysRoleDataService {

    @Override
    public int updateBatch(List<OpeSysRoleData> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeSysRoleData> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeSysRoleData> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysRoleData record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysRoleData record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

