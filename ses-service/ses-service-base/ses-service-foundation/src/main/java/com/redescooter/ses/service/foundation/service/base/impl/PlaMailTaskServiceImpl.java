package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaMailTaskMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTask;
import com.redescooter.ses.service.foundation.service.base.PlaMailTaskService;
@Service
public class PlaMailTaskServiceImpl extends ServiceImpl<PlaMailTaskMapper, PlaMailTask> implements PlaMailTaskService{

    @Override
    public int updateBatch(List<PlaMailTask> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaMailTask> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaMailTask record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaMailTask record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
    @Override
    public int insertOrUpdateWithBLOBs(PlaMailTask record) {
        return baseMapper.insertOrUpdateWithBLOBs(record);
    }
}
