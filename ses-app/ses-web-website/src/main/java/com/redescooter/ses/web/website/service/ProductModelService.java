package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.ProductModelDetailsResult;
import com.redescooter.ses.web.website.vo.product.addProductModelEnter;
import com.redescooter.ses.web.website.vo.product.modityProductModelEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/5 2:36 下午
 * @Description 产品车型服务
 **/
public interface ProductModelService {

    /**
     * 创建产品车型
     *
     * @param enter
     * @return
     */
    Boolean addProductModel(addProductModelEnter enter);

    /**
     * 编辑产品车型
     *
     * @param enter
     * @return
     */
    Boolean modityProductModel(modityProductModelEnter enter);

    /**
     * 移除产品车型
     *
     * @param enter
     * @return
     */
    Boolean removeProductModel(IdEnter enter);


    /**
     * 获取产品车型详情
     *
     * @param enter
     */
    ProductModelDetailsResult getProductModelDetails(IdEnter enter);


    /**
     * 获取产品车型列表
     *
     * @param enter
     */
    List<ProductModelDetailsResult> getProductModelList(GeneralEnter enter);

}
