package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPurchaseOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchaseOrder;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPurchaseOrderService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
@Service
public class OpeProductionPurchaseOrderServiceImpl extends ServiceImpl<OpeProductionPurchaseOrderMapper, OpeProductionPurchaseOrder>
        implements OpeProductionPurchaseOrderService {

    @Override
    public int updateBatch(List<OpeProductionPurchaseOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeProductionPurchaseOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPurchaseOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPurchaseOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPurchaseOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}






