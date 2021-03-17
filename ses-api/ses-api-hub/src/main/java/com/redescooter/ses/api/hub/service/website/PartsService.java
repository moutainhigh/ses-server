package com.redescooter.ses.api.hub.service.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.vo.website.SyncSalePartsDataEnter;

/**
 * @Description 销售配件数据同步到官网的接口
 * @Author Chris
 * @Date 2021/3/16 13:28
 */
public interface PartsService {

    /**
     * 销售配件数据同步到官网
     */
    GeneralResult syncSalePartsData(SyncSalePartsDataEnter enter);

}
