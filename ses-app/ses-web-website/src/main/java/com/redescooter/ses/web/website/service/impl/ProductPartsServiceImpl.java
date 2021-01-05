package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.service.ProductPartsService;
import com.redescooter.ses.web.website.vo.product.AddProductPartsEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductPartsEnter;
import com.redescooter.ses.web.website.vo.product.ProductPartsDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 4:33 上午
 * @Description 产品配件服务
 **/
@Slf4j
@Service
public class ProductPartsServiceImpl implements ProductPartsService {



    /**
     * 创建产品配件
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean addProductParts(AddProductPartsEnter enter) {







        return null;
    }

    /**
     * 编辑产品配件
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean modityProductParts(ModityProductPartsEnter enter) {
        return null;
    }

    /**
     * 移除产品配件
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean removeProductParts(IdEnter enter) {
        return null;
    }

    /**
     * 获取产品配件详情
     *
     * @param enter
     */
    @Override
    public ProductPartsDetailsResult getProductPartsDetails(IdEnter enter) {
        return null;
    }

    /**
     * 获取产品配件列表
     *
     * @param enter
     */
    @Override
    public List<ProductPartsDetailsResult> getProductPartsList(GeneralEnter enter) {
        return null;
    }
}
