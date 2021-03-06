package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeStockBillMapper;
import com.redescooter.ses.mobile.rps.dm.OpeStockBill;
import com.redescooter.ses.mobile.rps.service.base.OpeStockBillService;

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





