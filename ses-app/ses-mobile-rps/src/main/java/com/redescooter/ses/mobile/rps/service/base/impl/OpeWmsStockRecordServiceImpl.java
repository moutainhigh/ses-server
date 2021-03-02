package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockRecord;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsStockRecordService;

/**
 * @author assert
 * @date 2021/1/14 15:45
 */
@Service
public class OpeWmsStockRecordServiceImpl extends ServiceImpl<OpeWmsStockRecordMapper, OpeWmsStockRecord>
        implements OpeWmsStockRecordService {

    @Override
    public int updateBatch(List<OpeWmsStockRecord> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeWmsStockRecord> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeWmsStockRecord> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsStockRecord record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsStockRecord record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


