package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.website.vo.delivery.DeliveryModeResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 7:55 下午
 * @Description 取货方式
 **/
public interface DeliveryService {

    /**
     * 配送方式列表
     *
     * @param enter
     * @return
     */
    List<DeliveryModeResult> deliveryTypelist(GeneralEnter enter);

}
