package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.ColourDetailsResult;
import com.redescooter.ses.web.website.vo.colour.AddColourEnter;
import com.redescooter.ses.web.website.vo.colour.ModityColourEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/5 10:12 下午
 * @Description 产品颜色服务
 **/
public interface ColourService {
    /**
     * 创建产品颜色
     *
     * @param enter
     * @return
     */
    Boolean addColour(AddColourEnter enter);

    /**
     * 编辑产品颜色
     *
     * @param enter
     * @return
     */
    Boolean modityColour(ModityColourEnter enter);

    /**
     * 移除产品颜色
     *
     * @param enter
     * @return
     */
    Boolean removeColour(IdEnter enter);


    /**
     * 获取产品颜色详情
     *
     * @param enter
     */
    ColourDetailsResult getColourDetails(IdEnter enter);


    /**
     * 获取产品颜色列表
     *
     * @param enter
     */
    List<ColourDetailsResult> getColourList(GeneralEnter enter);
}
