package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeStockProdPart;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeStockProdPartMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeStockProdPartService;

@Service
public class OpeStockProdPartServiceImpl extends ServiceImpl<OpeStockProdPartMapper, OpeStockProdPart> implements OpeStockProdPartService {

    @Override
    public int updateBatch(List<OpeStockProdPart> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeStockProdPart> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeStockProdPart record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeStockProdPart record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


