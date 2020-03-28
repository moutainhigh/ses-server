package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeStockBillMapper;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeStockBillServiceImpl extends ServiceImpl<OpeStockBillMapper, OpeStockBill> implements OpeStockBillService {

    @Override
    public int updateBatch(List<OpeStockBill> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeStockBill> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeStockBill record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeStockBill record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



