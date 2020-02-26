package com.redescooter.ses.web.ros.service.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsMapper;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.service.base.OpePartsService;

@Service
public class OpePartsServiceImpl extends ServiceImpl<OpePartsMapper, OpeParts> implements OpePartsService {

    @Override
    public int updateBatch(List<OpeParts> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeParts> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeParts record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeParts record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

