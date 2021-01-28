package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsScooterStockService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@Service
public class OpeWmsScooterStockServiceImpl extends ServiceImpl<OpeWmsScooterStockMapper, OpeWmsScooterStock> implements OpeWmsScooterStockService{


    @Override
    public int updateBatch(List<OpeWmsScooterStock> list) {
        return 0;
    }

    @Override
    public int batchInsert(List<OpeWmsScooterStock> list) {
        return 0;
    }

    @Override
    public int insertOrUpdate(OpeWmsScooterStock record) {
        return 0;
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsScooterStock record) {
        return 0;
    }
}
