package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
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
    GeneralResult addProductColour(AddProductColourEnter enter);

    /**
     * 编辑产品颜色关系
     *
     * @param enter
     * @return
     */
    GeneralResult modityProductColour(ModityProductColourEnter enter);

    /**
     * 移除产品颜色关系
     *
     * @param enter
     * @return
     */
    GeneralResult removeProductColour(IdEnter enter);

}
