package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.service.ProductColourService;
import com.redescooter.ses.web.website.vo.product.ProductColourDetailsResult;
import com.redescooter.ses.web.website.vo.product.addProductColourEnter;
import com.redescooter.ses.web.website.vo.product.modityProductColourEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/5 10:17 下午
 * @Description 产品颜色服务实现类
 **/
@Slf4j
@Service
public class ProductColourServiceImpl implements ProductColourService {
    /**
     * 创建产品颜色
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean addProductColour(addProductColourEnter enter) {
        return null;
    }

    /**
     * 编辑产品颜色
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean modityProductColour(modityProductColourEnter enter) {
        return null;
    }

    /**
     * 移除产品颜色
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean removeProductColour(IdEnter enter) {
        return null;
    }

    /**
     * 获取产品颜色详情
     *
     * @param enter
     */
    @Override
    public ProductColourDetailsResult getProductColourDetails(IdEnter enter) {
        return null;
    }

    /**
     * 获取产品颜色列表
     *
     * @param enter
     */
    @Override
    public List<ProductColourDetailsResult> getProductColourList(GeneralEnter enter) {
        return null;
    }
}
