package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.redescooter.ses.mobile.rps.dao.base.OpeWmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsCombinStockService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/14 15:44
 */
@Service
public class OpeWmsCombinStockServiceImpl extends ServiceImpl<OpeWmsCombinStockMapper, OpeWmsCombinStock>
        implements OpeWmsCombinStockService {

    @Override
    public int updateBatch(List<OpeWmsCombinStock> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeWmsCombinStock> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeWmsCombinStock> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsCombinStock record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsCombinStock record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


