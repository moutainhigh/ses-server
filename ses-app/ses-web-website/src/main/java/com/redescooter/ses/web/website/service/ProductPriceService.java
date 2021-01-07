package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.AddProductPriceEnter;
import com.redescooter.ses.web.website.vo.product.ProductPriceDetailsResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:24 上午
 * @Description 产品价格服务
 **/
public interface ProductPriceService {

    /**
     * 创建产品价格
     *
     * @param enter
     * @return
     */
    GeneralResult addProductPrice(AddProductPriceEnter enter);

    /**
     * 获取产品价格详情
     *
     * @param enter
     */
    ProductPriceDetailsResult getProductPriceDetails(IdEnter enter);

    /**
     * 移除产品价格详情
     *
     * @param enter
     */
    GeneralResult removeProductPrice(IdEnter enter);

    /**
     * 获取产品价格列表
     *
     * @param enter
     */
    List<ProductPriceDetailsResult> getProductPriceList(GeneralEnter enter);

}
