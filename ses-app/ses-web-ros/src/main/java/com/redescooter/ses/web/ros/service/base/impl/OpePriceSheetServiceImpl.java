package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePriceSheetMapper;
import com.redescooter.ses.web.ros.dm.OpePriceSheet;

import java.util.List;

import com.redescooter.ses.web.ros.service.base.OpePriceSheetService;

@Service
public class OpePriceSheetServiceImpl extends ServiceImpl<OpePriceSheetMapper, OpePriceSheet> implements OpePriceSheetService {

    @Override
    public int updateBatch(List<OpePriceSheet> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePriceSheet> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePriceSheet record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePriceSheet record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



