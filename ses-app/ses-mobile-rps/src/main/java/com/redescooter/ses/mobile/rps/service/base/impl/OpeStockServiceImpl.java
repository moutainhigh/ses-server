package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeStock;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeStockMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeStockService;

@Service
public class OpeStockServiceImpl extends ServiceImpl<OpeStockMapper, OpeStock> implements OpeStockService {

    @Override
    public int updateBatch(List<OpeStock> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeStock> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeStock record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeStock record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




