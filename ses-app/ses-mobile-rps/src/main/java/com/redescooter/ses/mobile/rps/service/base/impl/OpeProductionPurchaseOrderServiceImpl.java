package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchaseOrder;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPurchaseOrderMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPurchaseOrderService;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
@Service
public class OpeProductionPurchaseOrderServiceImpl implements OpeProductionPurchaseOrderService {

    @Resource
    private OpeProductionPurchaseOrderMapper opeProductionPurchaseOrderMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeProductionPurchaseOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpeProductionPurchaseOrder record) {
        return opeProductionPurchaseOrderMapper.insert(record);
    }

    @Override
    public int insertSelective(OpeProductionPurchaseOrder record) {
        return opeProductionPurchaseOrderMapper.insertSelective(record);
    }

    @Override
    public OpeProductionPurchaseOrder selectByPrimaryKey(Long id) {
        return opeProductionPurchaseOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeProductionPurchaseOrder record) {
        return opeProductionPurchaseOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeProductionPurchaseOrder record) {
        return opeProductionPurchaseOrderMapper.updateByPrimaryKey(record);
    }

}




