package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsQualifiedPartsStockMapper;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedPartsStock;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsQualifiedPartsStockService;
/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@Service
public class OpeWmsQualifiedPartsStockServiceImpl implements OpeWmsQualifiedPartsStockService{

    @Resource
    private OpeWmsQualifiedPartsStockMapper opeWmsQualifiedPartsStockMapper;

    @Override
    public int updateBatch(List<OpeWmsQualifiedPartsStock> list) {
        return opeWmsQualifiedPartsStockMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWmsQualifiedPartsStock> list) {
        return opeWmsQualifiedPartsStockMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsQualifiedPartsStock record) {
        return opeWmsQualifiedPartsStockMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsQualifiedPartsStock record) {
        return opeWmsQualifiedPartsStockMapper.insertOrUpdateSelective(record);
    }

}
