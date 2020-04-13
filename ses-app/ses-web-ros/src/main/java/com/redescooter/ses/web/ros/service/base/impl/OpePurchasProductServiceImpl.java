package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePurchasProductMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasProduct;
import com.redescooter.ses.web.ros.service.base.OpePurchasProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePurchasProductServiceImpl extends ServiceImpl<OpePurchasProductMapper, OpePurchasProduct> implements OpePurchasProductService {

    @Override
    public int updateBatch(List<OpePurchasProduct> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasProduct> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasProduct record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

