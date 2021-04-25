package com.redescooter.ses.api.hub.service.website;

import com.redescooter.ses.api.hub.vo.website.SyncProductionDataEnter;
import com.redescooter.ses.api.hub.vo.website.SyncSalePriceDataEnter;

import java.util.Map;

/**
 * @description: 销售产品数据同步到官网的接口
 * @author: Aleks
 * @create: 2021/03/16 09:57
 */
public interface ProductionService {

    // 如果是关闭操作 走这个方法  将之前同步的数据清除
    void syncByProductionCode(String productionName,Integer saleStatus);

    //开启的时候同步数据（5张表）
    void syncProductionData(SyncProductionDataEnter syncProductionDataEnter);

    // ros那边删除数据的时候 官网对应的数据也要删除
    void syncDeleteData(String productionName);

    // 根据site_product_model表的主键找到名字,颜色,型号
    Map<String, String> getProductInfoByModelId(Long id);

    /**
     * 同步销售价格,关闭的时候和删除的时候调
     */
    void syncDeleteSalePrice(String scooterBattery, Integer type, Integer period);

    /**
     * 同步销售价格,开启的时候调
     */
    void syncSalePrice(SyncSalePriceDataEnter enter);

}
