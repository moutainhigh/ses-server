package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsStockSerialNumberService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 20:10
 */
@Service
public class OpeWmsStockSerialNumberServiceImpl extends ServiceImpl<OpeWmsStockSerialNumberMapper, OpeWmsStockSerialNumber>
        implements OpeWmsStockSerialNumberService {

    @Override
    public int updateBatch(List<OpeWmsStockSerialNumber> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeWmsStockSerialNumber> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeWmsStockSerialNumber> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsStockSerialNumber record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsStockSerialNumber record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



