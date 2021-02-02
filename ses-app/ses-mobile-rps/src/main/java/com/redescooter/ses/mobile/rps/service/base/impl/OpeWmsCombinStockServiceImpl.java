package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

import com.redescooter.ses.mobile.rps.dao.base.OpeWmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsCombinStockService;
/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@Service
public class OpeWmsCombinStockServiceImpl extends ServiceImpl<OpeWmsCombinStockMapper, OpeWmsCombinStock> implements OpeWmsCombinStockService{


    @Override
    public int updateBatch(List<OpeWmsCombinStock> list) {
        return 0;
    }

    @Override
    public int batchInsert(List<OpeWmsCombinStock> list) {
        return 0;
    }

    @Override
    public int insertOrUpdate(OpeWmsCombinStock record) {
        return 0;
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsCombinStock record) {
        return 0;
    }


    @Override
    public boolean saveOrUpdate(OpeWmsCombinStock entity) {
        return false;
    }

}
