package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeFrStockBill;
import com.redescooter.ses.web.ros.dao.base.OpeFrStockBillMapper;
import com.redescooter.ses.web.ros.service.base.OpeFrStockBillService;

@Service
public class OpeFrStockBillServiceImpl extends ServiceImpl<OpeFrStockBillMapper, OpeFrStockBill> implements OpeFrStockBillService {

    @Override
    public int updateBatch(List<OpeFrStockBill> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeFrStockBill> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeFrStockBill record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeFrStockBill record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

