package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpePurchasProduct;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasProductMapper;
import java.util.List;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasProductService;
@Service
public class OpePurchasProductServiceImpl extends ServiceImpl<OpePurchasProductMapper, OpePurchasProduct> implements OpePurchasProductService{

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
