package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/28 15:04
 */
public interface WmsStockService {

    /**
     * 校验
     */
    GeneralResult handle(String rsn, Long userId);

}
