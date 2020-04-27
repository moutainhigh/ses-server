package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.dao.base.OpeStockProdProductMapper;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
@Service
public class OpeStockProdProductServiceImpl extends ServiceImpl<OpeStockProdProductMapper, OpeStockProdProduct> implements OpeStockProdProductService{

    @Override
    public int updateBatch(List<OpeStockProdProduct> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeStockProdProduct> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeStockProdProduct record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeStockProdProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

