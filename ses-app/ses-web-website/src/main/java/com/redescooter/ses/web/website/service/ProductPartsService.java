package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.website.vo.product.AddProductPartsEnter;

/**
 * @Author jerry
 * @Date 2021/1/6 3:25 上午
 * @Description 产品配置服务
 **/
public interface ProductPartsService {

    /**
     * 创建产品配置
     *
     * @param enter
     * @return
     */
    GeneralResult addProductParts(AddProductPartsEnter enter);
}
