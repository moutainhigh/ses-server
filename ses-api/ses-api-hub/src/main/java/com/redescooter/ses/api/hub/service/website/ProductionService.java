package com.redescooter.ses.api.hub.service.website;

import com.redescooter.ses.api.hub.vo.website.SyncProductionDataEnter;

/**
 * @description: 销售产品数据同步到官网的接口
 * @author: Aleks
 * @create: 2021/03/16 09:57
 */
public interface ProductionService {

    // 通过产品名称 判断改产品是否已经同步过（如果之前同步过，本次就是关闭销售状态，将之前的数据删除）
    boolean syncByProductionCode(String productionName,Integer saleStatus);

    //同步数据（5张表）
    void syncProductionData(SyncProductionDataEnter syncProductionDataEnter);

    // ros那边删除数据的时候 官网对应的数据也要删除
    void syncDeleteData(String productionName);

}
