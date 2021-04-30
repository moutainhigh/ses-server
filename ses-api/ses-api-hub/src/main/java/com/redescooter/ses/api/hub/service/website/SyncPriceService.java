package com.redescooter.ses.api.hub.service.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.vo.website.SyncSalePriceDataEnter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/27 11:16
 */
public interface SyncPriceService {

    /**
     * 同步销售价格,关闭的时候和删除的时候调
     */
    GeneralResult syncDeleteSalePrice(String scooterBattery, Integer type, Integer period);

    /**
     * 同步销售价格,开启的时候调
     */
    GeneralResult syncSalePrice(SyncSalePriceDataEnter enter);

}
