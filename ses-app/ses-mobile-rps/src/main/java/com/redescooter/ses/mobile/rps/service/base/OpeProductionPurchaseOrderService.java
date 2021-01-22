package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchaseOrder;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
public interface OpeProductionPurchaseOrderService {


    int deleteByPrimaryKey(Long id);

    int insert(OpeProductionPurchaseOrder record);

    int insertSelective(OpeProductionPurchaseOrder record);

    OpeProductionPurchaseOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductionPurchaseOrder record);

    int updateByPrimaryKey(OpeProductionPurchaseOrder record);

}




