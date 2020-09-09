package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.SellsyProductMapper;
import com.redescooter.ses.web.ros.dm.SellsyProduct;
import com.redescooter.ses.web.ros.service.base.SellsyProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellsyProductServiceImpl extends ServiceImpl<SellsyProductMapper, SellsyProduct> implements SellsyProductService {

    @Override
    public int updateBatch(List<SellsyProduct> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SellsyProduct> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SellsyProduct record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SellsyProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

