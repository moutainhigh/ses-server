package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasMapper;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePurchas;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasService;

@Service
public class OpePurchasServiceImpl extends ServiceImpl<OpePurchasMapper, OpePurchas> implements OpePurchasService {

    @Override
    public int updateBatch(List<OpePurchas> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchas> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchas record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchas record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

