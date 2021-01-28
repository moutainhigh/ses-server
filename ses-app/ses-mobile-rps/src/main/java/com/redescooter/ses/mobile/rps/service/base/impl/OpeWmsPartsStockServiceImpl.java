package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsPartsStockService;
/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@Service
public class OpeWmsPartsStockServiceImpl extends ServiceImpl<OpeWmsPartsStockMapper, OpeWmsPartsStock> implements OpeWmsPartsStockService{

    @Resource
    private OpeWmsPartsStockMapper opeWmsPartsStockMapper;

    @Override
    public int updateBatch(List<OpeWmsPartsStock> list) {
        return opeWmsPartsStockMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWmsPartsStock> list) {
        return opeWmsPartsStockMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWmsPartsStock record) {
        return opeWmsPartsStockMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWmsPartsStock record) {
        return opeWmsPartsStockMapper.insertOrUpdateSelective(record);
    }

}
