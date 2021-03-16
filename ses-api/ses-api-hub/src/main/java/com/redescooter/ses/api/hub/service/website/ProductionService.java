package com.redescooter.ses.api.hub.service.website;

import com.redescooter.ses.api.hub.vo.website.SyncProductionDataEnter;

/**
 * @description: 销售产品数据同步到官网的接口
 * @author: Aleks
 * @create: 2021/03/16 09:57
 */
public interface ProductionService {

    // 通过产品编码 判断改产品是否已经同步过（如果之前同步过，本次只需改状态即可）
    boolean syncByProductionCode(String productionCode,Integer saleStatus);

    //同步数据（5张表）
    void syncProductionData(SyncProductionDataEnter syncProductionDataEnter);

}
