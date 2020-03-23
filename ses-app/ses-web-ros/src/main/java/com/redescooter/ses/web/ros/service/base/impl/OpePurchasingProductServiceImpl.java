package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpePurchasingProduct;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchasingProductMapper;
import com.redescooter.ses.web.ros.service.base.OpePurchasingProductService;

@Service
public class OpePurchasingProductServiceImpl extends ServiceImpl<OpePurchasingProductMapper, OpePurchasingProduct> implements OpePurchasingProductService {

    @Override
    public int updateBatch(List<OpePurchasingProduct> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasingProduct> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasingProduct record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasingProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

