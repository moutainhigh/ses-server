package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.delivery.vo.edorder.GetAddressOfLonLatEnter;

/**
 * @ClassName EdAddressServices
 * @Author Jerry
 * @date 2020/02/24 11:35
 * @Description:快递单据地址服务
 */
public interface EdAddressServices {

    /**
     * 快递订单地址经纬度更新
     *
     * @param enter
     * @return
     * @Author Jerry
     */
    GeneralResult getAddressOfLonLat(GetAddressOfLonLatEnter enter);
}
