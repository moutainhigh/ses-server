package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSaleArea;
import com.redescooter.ses.web.ros.dao.base.OpeSaleAreaMapper;
import com.redescooter.ses.web.ros.service.base.OpeSaleAreaService;

@Service
public class OpeSaleAreaServiceImpl extends ServiceImpl<OpeSaleAreaMapper, OpeSaleArea> implements OpeSaleAreaService {

    @Override
    public int updateBatch(List<OpeSaleArea> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeSaleArea> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeSaleArea> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSaleArea record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSaleArea record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

