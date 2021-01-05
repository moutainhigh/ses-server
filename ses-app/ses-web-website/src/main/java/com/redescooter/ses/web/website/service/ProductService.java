package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.AddProductEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductEnter;
import com.redescooter.ses.web.website.vo.product.ProductDetailsResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 1:20 上午
 * @Description 产品服务
 **/
public interface ProductService {

    /**
     * 创建产品
     *
     * @param enter
     * @return
     */
    Boolean addProduct(AddProductEnter enter);

    /**
     * 编辑产品
     *
     * @param enter
     * @return
     */
    Boolean modityProduct(ModityProductEnter enter);

    /**
     * 移除产品
     *
     * @param enter
     * @return
     */
    Boolean removeProduct(IdEnter enter);


    /**
     * 获取产品详情
     *
     * @param enter
     */
    ProductDetailsResult getProductDetails(IdEnter enter);


    /**
     * 获取产品列表
     *
     * @param enter
     */
    List<ProductDetailsResult> getProductList(GeneralEnter enter);

}
