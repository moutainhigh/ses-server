package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockRecord;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsStockRecordService;
/**
 *@author assert
 *@date 2021/1/14 15:45
 */
@Service
public class OpeWmsStockRecordServiceImpl implements OpeWmsStockRecordService{

    @Resource
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;

    @Override
    public int updateBatch(List<OpeWmsStockRecord> list) {
        return opeWmsStockRecordMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWmsStockRecord> list) {
        return opeWmsStockRecordMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsStockRecord record) {
        return opeWmsStockRecordMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsStockRecord record) {
        return opeWmsStockRecordMapper.insertOrUpdateSelective(record);
    }

}
