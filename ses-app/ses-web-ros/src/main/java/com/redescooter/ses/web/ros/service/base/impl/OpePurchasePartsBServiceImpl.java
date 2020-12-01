package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchasePartsBMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasePartsB;
import com.redescooter.ses.web.ros.service.base.OpePurchasePartsBService;

@Service
public class OpePurchasePartsBServiceImpl extends ServiceImpl<OpePurchasePartsBMapper, OpePurchasePartsB> implements OpePurchasePartsBService {

    @Override
    public int updateBatch(List<OpePurchasePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasePartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasePartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasePartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
