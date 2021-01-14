package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsQualifiedCombinStockMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedCombinStock;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsQualifiedCombinStockService;
/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@Service
public class OpeWmsQualifiedCombinStockServiceImpl implements OpeWmsQualifiedCombinStockService{

    @Resource
    private OpeWmsQualifiedCombinStockMapper opeWmsQualifiedCombinStockMapper;

    @Override
    public int updateBatch(List<OpeWmsQualifiedCombinStock> list) {
        return opeWmsQualifiedCombinStockMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWmsQualifiedCombinStock> list) {
        return opeWmsQualifiedCombinStockMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsQualifiedCombinStock record) {
        return opeWmsQualifiedCombinStockMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsQualifiedCombinStock record) {
        return opeWmsQualifiedCombinStockMapper.insertOrUpdateSelective(record);
    }

}
