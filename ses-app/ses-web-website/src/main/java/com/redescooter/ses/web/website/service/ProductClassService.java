package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.ProductClassDetailsResult;
import com.redescooter.ses.web.website.vo.product.addProductClassEnter;
import com.redescooter.ses.web.website.vo.product.modityProductClassEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/5 2:36 下午
 * @Description 产品种类服务
 **/
public interface ProductClassService {

    /**
     * 创建产品种类
     *
     * @param enter
     * @return
     */
    Boolean addProductClass(addProductClassEnter enter);

    /**
     * 编辑产品种类
     *
     * @param enter
     * @return
     */
    Boolean modityProductClass(modityProductClassEnter enter);

    /**
     * 移除产品种类
     *
     * @param enter
     * @return
     */
    Boolean removeProductClass(IdEnter enter);


    /**
     * 获取产品种类详情
     *
     * @param enter
     */
    ProductClassDetailsResult getProductClassDetails(IdEnter enter);


    /**
     * 获取产品种类列表
     *
     * @param enter
     */
    List<ProductClassDetailsResult> getProductClassList(GeneralEnter enter);

}
