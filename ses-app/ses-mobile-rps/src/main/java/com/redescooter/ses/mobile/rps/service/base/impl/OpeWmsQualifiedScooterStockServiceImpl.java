package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsQualifiedScooterStockMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedScooterStock;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsQualifiedScooterStockService;

/**
 * @author assert
 * @date 2021/1/14 15:44
 */
@Service
public class OpeWmsQualifiedScooterStockServiceImpl extends ServiceImpl<OpeWmsQualifiedScooterStockMapper, OpeWmsQualifiedScooterStock>
        implements OpeWmsQualifiedScooterStockService {

    @Resource
    private OpeWmsQualifiedScooterStockMapper opeWmsQualifiedScooterStockMapper;

    @Override
    public int updateBatch(List<OpeWmsQualifiedScooterStock> list) {
        return opeWmsQualifiedScooterStockMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWmsQualifiedScooterStock> list) {
        return opeWmsQualifiedScooterStockMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsQualifiedScooterStock record) {
        return opeWmsQualifiedScooterStockMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsQualifiedScooterStock record) {
        return opeWmsQualifiedScooterStockMapper.insertOrUpdateSelective(record);
    }

}





