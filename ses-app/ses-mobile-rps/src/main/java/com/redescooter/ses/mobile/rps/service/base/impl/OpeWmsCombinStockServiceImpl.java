package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsCombinStockService;
/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@Service
public class OpeWmsCombinStockServiceImpl implements OpeWmsCombinStockService{

    @Resource
    private OpeWmsCombinStockMapper opeWmsCombinStockMapper;

    @Override
    public int updateBatch(List<OpeWmsCombinStock> list) {
        return opeWmsCombinStockMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWmsCombinStock> list) {
        return opeWmsCombinStockMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsCombinStock record) {
        return opeWmsCombinStockMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsCombinStock record) {
        return opeWmsCombinStockMapper.insertOrUpdateSelective(record);
    }

}
