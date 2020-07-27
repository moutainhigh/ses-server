package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeFrStockMapper;
import com.redescooter.ses.web.ros.dm.OpeFrStock;
import com.redescooter.ses.web.ros.service.base.OpeFrStockService;
@Service
public class OpeFrStockServiceImpl extends ServiceImpl<OpeFrStockMapper, OpeFrStock> implements OpeFrStockService{

    @Override
    public int updateBatch(List<OpeFrStock> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeFrStock> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeFrStock record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeFrStock record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
