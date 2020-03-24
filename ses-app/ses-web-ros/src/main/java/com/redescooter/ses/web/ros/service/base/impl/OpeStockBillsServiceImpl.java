package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeStockBillsMapper;
import com.redescooter.ses.web.ros.dm.OpeStockBills;
import com.redescooter.ses.web.ros.service.base.OpeStockBillsService;

@Service
public class OpeStockBillsServiceImpl extends ServiceImpl<OpeStockBillsMapper, OpeStockBills> implements OpeStockBillsService {

    @Override
    public int updateBatch(List<OpeStockBills> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeStockBills> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeStockBills record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeStockBills record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
