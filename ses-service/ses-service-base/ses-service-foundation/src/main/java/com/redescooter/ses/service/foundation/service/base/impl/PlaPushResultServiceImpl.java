package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaPushResult;
import com.redescooter.ses.service.foundation.dao.base.PlaPushResultMapper;
import com.redescooter.ses.service.foundation.service.base.PlaPushResultService;
@Service
public class PlaPushResultServiceImpl extends ServiceImpl<PlaPushResultMapper, PlaPushResult> implements PlaPushResultService{

    @Override
    public int updateBatch(List<PlaPushResult> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaPushResult> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaPushResult record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaPushResult record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
