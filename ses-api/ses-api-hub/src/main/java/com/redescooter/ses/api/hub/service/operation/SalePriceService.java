package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 19:55
 */
public interface SalePriceService {

    void deleteSalePrice(String modelName);

    List<String> modelPriceList(GeneralEnter enter);
}
