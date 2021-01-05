package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.ProductColourDetailsResult;
import com.redescooter.ses.web.website.vo.product.addProductColourEnter;
import com.redescooter.ses.web.website.vo.product.modityProductColourEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/5 10:12 下午
 * @Description 产品颜色服务
 **/
public interface ProductColourService {
    /**
     * 创建产品颜色
     *
     * @param enter
     * @return
     */
    Boolean addProductColour(addProductColourEnter enter);

    /**
     * 编辑产品颜色
     *
     * @param enter
     * @return
     */
    Boolean modityProductColour(modityProductColourEnter enter);

    /**
     * 移除产品颜色
     *
     * @param enter
     * @return
     */
    Boolean removeProductColour(IdEnter enter);


    /**
     * 获取产品颜色详情
     *
     * @param enter
     */
    ProductColourDetailsResult getProductColourDetails(IdEnter enter);


    /**
     * 获取产品颜色列表
     *
     * @param enter
     */
    List<ProductColourDetailsResult> getProductColourList(GeneralEnter enter);
}
