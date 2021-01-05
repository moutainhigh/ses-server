package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.AddProductPartsEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductPartsEnter;
import com.redescooter.ses.web.website.vo.product.ProductPartsDetailsResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:25 上午
 * @Description 产品配件配置服务
 **/
public interface ProductPartsService {

    /**
     * 创建产品配件
     *
     * @param enter
     * @return
     */
    Boolean addProductParts(AddProductPartsEnter enter);

    /**
     * 编辑产品配件
     *
     * @param enter
     * @return
     */
    Boolean modityProductParts(ModityProductPartsEnter enter);

    /**
     * 移除产品配件
     *
     * @param enter
     * @return
     */
    Boolean removeProductParts(IdEnter enter);


    /**
     * 获取产品配件详情
     *
     * @param enter
     */
    ProductPartsDetailsResult getProductPartsDetails(IdEnter enter);


    /**
     * 获取产品配件列表
     *
     * @param enter
     */
    List<ProductPartsDetailsResult> getProductPartsList(GeneralEnter enter);

}
