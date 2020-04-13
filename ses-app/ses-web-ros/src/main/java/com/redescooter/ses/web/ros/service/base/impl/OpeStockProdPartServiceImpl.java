package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeStockProdPartMapper;
import com.redescooter.ses.web.ros.dm.OpeStockProdPart;
import com.redescooter.ses.web.ros.service.base.OpeStockProdPartService;
import org.springframework.stereotype.Service;

import java.util.List;

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

