package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeFrStockProduct;
import com.redescooter.ses.web.ros.dao.base.OpeFrStockProductMapper;
import com.redescooter.ses.web.ros.service.base.OpeFrStockProductService;
@Service
public class OpeFrStockProductServiceImpl extends ServiceImpl<OpeFrStockProductMapper, OpeFrStockProduct> implements OpeFrStockProductService{

    @Override
    public int updateBatch(List<OpeFrStockProduct> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeFrStockProduct> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeFrStockProduct record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeFrStockProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
