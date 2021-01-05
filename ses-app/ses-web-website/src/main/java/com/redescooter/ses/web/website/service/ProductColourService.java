package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.AddProductColourEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductColourEnter;

/**
 * @Author jerry
 * @Date 2021/1/6 1:00 上午
 * @Description 产品颜色关系服务
 **/
public interface ProductColourService {

    /**
     * 创建产品颜色关系
     *
     * @param enter
     * @return
     */
    Boolean addProductColour(AddProductColourEnter enter);

    /**
     * 编辑产品颜色关系
     *
     * @param enter
     * @return
     */
    Boolean modityProductColour(ModityProductColourEnter enter);

    /**
     * 移除产品颜色关系
     *
     * @param enter
     * @return
     */
    Boolean removeProductColour(IdEnter enter);

}
