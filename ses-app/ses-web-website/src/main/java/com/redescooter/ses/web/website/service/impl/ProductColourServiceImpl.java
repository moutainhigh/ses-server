package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductColour;
import com.redescooter.ses.web.website.service.ProductColourService;
import com.redescooter.ses.web.website.service.base.SiteProductColourService;
import com.redescooter.ses.web.website.vo.product.AddProductColourEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductColourEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author jerry
 * @Date 2021/1/6 1:01 上午
 * @Description 产品颜色关系服务实现类
 **/
@Slf4j
@Service
public class ProductColourServiceImpl implements ProductColourService {

    @Autowired(required = true)
    private SiteProductColourService siteProductColourService;

    @Reference
    private IdAppService idAppService;

    /**
     * 创建产品颜色关系
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean addProductColour(AddProductColourEnter enter) {
        SiteProductColour addProductColourVO = new SiteProductColour();
        addProductColourVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_COLOUR));
        addProductColourVO.setColourId(enter.getColourId());
        addProductColourVO.setProductId(enter.getProductId());

        addProductColourVO.setRevision(0);
        addProductColourVO.setSynchronizeFlag(false);

        return siteProductColourService.save(addProductColourVO);
    }

    /**
     * 编辑产品颜色关系
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean modityProductColour(ModityProductColourEnter enter) {

        SiteProductColour modityProductColourVO = new SiteProductColour();
        BeanUtils.copyProperties(enter, modityProductColourVO);

        return siteProductColourService.update(modityProductColourVO, new UpdateWrapper<SiteProductColour>().eq(SiteProductColour.COL_ID, enter.getId()));
    }

    /**
     * 移除产品颜色关系
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean removeProductColour(IdEnter enter) {
        return siteProductColourService.removeById(enter.getId());
    }

}
