package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsScooterStockService;
/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@Service
public class OpeWmsScooterStockServiceImpl implements OpeWmsScooterStockService{

    @Resource
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @Override
    public int updateBatch(List<OpeWmsScooterStock> list) {
        return opeWmsScooterStockMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWmsScooterStock> list) {
        return opeWmsScooterStockMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsScooterStock record) {
        return opeWmsScooterStockMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsScooterStock record) {
        return opeWmsScooterStockMapper.insertOrUpdateSelective(record);
    }

}
