package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPermissionMapper;
import com.redescooter.ses.service.foundation.service.base.PlaUserPermissionService;
@Service
public class PlaUserPermissionServiceImpl extends ServiceImpl<PlaUserPermissionMapper, PlaUserPermission> implements PlaUserPermissionService{

    @Override
    public int updateBatch(List<PlaUserPermission> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaUserPermission> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaUserPermission record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaUserPermission record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
