package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeStockPurchas;
import com.redescooter.ses.web.ros.dao.base.OpeStockPurchasMapper;
import com.redescooter.ses.web.ros.service.base.OpeStockPurchasService;
@Service
public class OpeStockPurchasServiceImpl extends ServiceImpl<OpeStockPurchasMapper, OpeStockPurchas> implements OpeStockPurchasService{

    @Override
    public int updateBatch(List<OpeStockPurchas> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeStockPurchas> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeStockPurchas record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeStockPurchas record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
